package no.difi.meldingsutveksling.noarkexchange.receive;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.core.*;
import no.difi.meldingsutveksling.domain.MeldingsUtvekslingRuntimeException;
import no.difi.meldingsutveksling.domain.Payload;
import no.difi.meldingsutveksling.domain.sbdh.StandardBusinessDocument;
import no.difi.meldingsutveksling.kvittering.xsd.Kvittering;
import no.difi.meldingsutveksling.logging.Audit;
import no.difi.meldingsutveksling.nextmove.*;
import no.difi.meldingsutveksling.noarkexchange.*;
import no.difi.meldingsutveksling.noarkexchange.schema.AppReceiptType;
import no.difi.meldingsutveksling.noarkexchange.schema.PutMessageRequestType;
import no.difi.meldingsutveksling.noarkexchange.schema.StatusMessageType;
import no.difi.meldingsutveksling.receipt.ConversationService;
import no.difi.meldingsutveksling.receipt.GenericReceiptStatus;
import no.difi.meldingsutveksling.receipt.MessageStatus;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import static no.difi.meldingsutveksling.logging.MessageMarkerFactory.markerFrom;

/**
 * The idea behind this queue is to avoid loosing messages before they are saved in Noark System.
 * <p>
 * The way it works is that any exceptions that happens in after a message is put on the queue is re-sent to the JMS listener. If
 * the application is restarted the message is also resent.
 * <p>
 * The JMS listener has the responsibility is to forward the message to the archive system.
 */
@Component
public class InternalQueue {

    private static final String EXTERNAL = "external";
    private static final String NOARK = "noark";
    private static final String NEXTMOVE = "nextmove";
    private static final String PUTMSG = "putmessage";
    private static final String DLQ = "ActiveMQ.DLQ";

    private Logger logger = LoggerFactory.getLogger(InternalQueue.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    private IntegrajonspunktReceiveImpl integrajonspunktReceive;

    @Autowired
    private IntegrasjonspunktProperties properties;

    @Autowired
    private EDUCoreSender eduCoreSender;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private NextMoveSender nextMoveSender;

    @Autowired
    private ObjectMapper objectMapper;

    private NoarkClient noarkClient;

    private EDUCoreFactory eduCoreFactory;

    private static JAXBContext jaxbContext;
    private static JAXBContext jaxbContextNextmove;

    private final DocumentConverter documentConverter = new DocumentConverter();

    @Autowired
    InternalQueue(ObjectProvider<IntegrajonspunktReceiveImpl> integrajonspunktReceive,
                  @Qualifier("localNoark") ObjectProvider<NoarkClient> noarkClient,
                  EDUCoreFactory eduCoreFactory) {
        this.integrajonspunktReceive = integrajonspunktReceive.getIfAvailable();
        this.noarkClient = noarkClient.getIfAvailable();
        this.eduCoreFactory = eduCoreFactory;
    }

    static {
        try {
            jaxbContext = JAXBContextFactory.createContext(new Class[]{StandardBusinessDocument.class, Payload.class, Kvittering.class, PutMessageRequestType.class}, null);
            jaxbContextNextmove = JAXBContextFactory.createContext(new Class[]{ConversationResource.class}, null);
        } catch (JAXBException e) {
            throw new RuntimeException("Could not start internal queue: Failed to create JAXBContext", e);
        }
    }

    @JmsListener(destination = NEXTMOVE, containerFactory = "myJmsContainerFactory", concurrency = "100")
    public void nextMove2Listener(byte[] message, Session session) {
        NextMoveMessage nextMoveMessage;
        try {
            nextMoveMessage = objectMapper.readValue(message, NextMoveMessage.class);
        } catch (IOException e) {
            throw new NextMoveRuntimeException("Unable to unmarshall NextMove message from queue", e);
        }
        try {
            nextMoveSender.send(nextMoveMessage);
        } catch (NextMoveException e) {
            throw new NextMoveRuntimeException("Unable to send NextMove message", e);
        }
    }

    @JmsListener(destination = NOARK, containerFactory = "myJmsContainerFactory")
    public void noarkListener(byte[] message, Session session) {
        StandardBusinessDocument sbd = documentConverter.unmarshallFrom(message);
        try {
            forwardToNoark(sbd);
        } catch (Exception e) {
            Audit.warn("Failed to forward message.. queue will retry", sbd.createLogstashMarkers(), e);
            throw e;
        }
    }

    @JmsListener(destination = PUTMSG, containerFactory = "myJmsContainerFactory")
    public void putMessageListener(byte[] message, Session session) {
        PutMessageRequestType putMessage = unmarshalPutMessage(message);
        try {
            noarkClient.sendEduMelding(putMessage);
        } catch (Exception e) {
            Audit.warn("Failed to forward message.. queue will retry", PutMessageMarker.markerFrom(new PutMessageRequestWrapper(putMessage)), e);
            throw e;
        }
    }

    @JmsListener(destination = EXTERNAL, containerFactory = "myJmsContainerFactory")
    public void externalListener(byte[] message, Session session) {
        EDUCore request = EDUCoreConverter.unmarshallFrom(message);
        try {
            eduCoreSender.sendMessage(request);
        } catch (Exception e) {
            Audit.warn("Failed to send message... queue will retry", EDUCoreMarker.markerFrom(request), e);
            throw e;
        }
    }

    /**
     * Log failed messages as errors
     */
    @SuppressWarnings("squid:S1166")
    @JmsListener(destination = DLQ)
    public void dlqListener(byte[] message, Session session) {
        MessageStatus ms = MessageStatus.of(GenericReceiptStatus.FEIL);
        String conversationId = "";
        String errorMsg = "";

        try {
            EDUCore request = EDUCoreConverter.unmarshallFrom(message);
            errorMsg = "Failed to send message. Moved to DLQ";
            Audit.error(errorMsg, EDUCoreMarker.markerFrom(request));
            conversationId = request.getId();
            if (noarkClient != null) {
                sendErrorAppReceipt(request);
            }
            if (properties.getFeature().isDumpDlqMessages()) {
                writeMessageToDisk(request);
            }
        } catch (Exception e) {
        }

        try {
            StandardBusinessDocument sbd = documentConverter.unmarshallFrom(message);
            errorMsg = "Failed to forward message. Moved to DLQ.";
            Audit.error(errorMsg, sbd.createLogstashMarkers());
            conversationId = sbd.getConversationId();
            sendErrorAppReceipt(sbd);
        } catch (Exception e) {
        }

        try {
            ConversationResource cr = unmarshalNextMoveMessage(message);
            errorMsg = "Failed to send message. Moved to DLQ";
            Audit.error(errorMsg, NextMoveMessageMarkers.markerFrom(cr));
            conversationId = cr.getConversationId();
        } catch (Exception e) {
        }

        ms.setDescription(errorMsg);
        conversationService.registerStatus(conversationId, ms);
    }

    private void writeMessageToDisk(EDUCore request) {
        PutMessageRequestType putMessage = EDUCoreFactory.createPutMessageFromCore(request);
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            marshaller.marshal(new JAXBElement<>(new QName("uri", "local"), PutMessageRequestType.class, putMessage), bos);
            FileOutputStream fos = new FileOutputStream(new File("failed_messages/" + request.getId() + "_failed.xml"));
            bos.writeTo(fos);
        } catch (JAXBException | IOException e) {
            logger.error("Failed writing message to disk", e);
        }
    }

    private ConversationResource unmarshalNextMoveMessage(byte[] message) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(message);
            StreamSource ss = new StreamSource(bis);
            Unmarshaller unmarshaller = jaxbContextNextmove.createUnmarshaller();
            return unmarshaller.unmarshal(ss, ConversationResource.class).getValue();
        } catch (JAXBException e) {
            logger.error("Could not unmarshal nextmove message", e);
            throw new MeldingsUtvekslingRuntimeException(e);
        }
    }

    private PutMessageRequestType unmarshalPutMessage(byte[] message) {
        ByteArrayInputStream bis = new ByteArrayInputStream(message);
        StreamSource ss = new StreamSource(bis);
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(ss, PutMessageRequestType.class).getValue();
        } catch (JAXBException e) {
            logger.error("Could not unmarshal putmessage", e);
            throw new MeldingsUtvekslingRuntimeException(e);
        }
    }

    private void sendErrorAppReceipt(EDUCore request) {
        AppReceiptType receipt = new AppReceiptType();
        receipt.setType("ERROR");
        StatusMessageType statusMessageType = new StatusMessageType();
        statusMessageType.setCode("ID");
        statusMessageType.setText(String.format("Feilet ved sending til %s", request.getServiceIdentifier()));
        receipt.getMessage().add(statusMessageType);

        Object oldPayload = request.getPayload();
        request.swapSenderAndReceiver();
        request.setMessageType(EDUCore.MessageType.APPRECEIPT);
        request.setPayload(EDUCoreConverter.appReceiptAsString(receipt));
        PutMessageRequestType putMessage = EDUCoreFactory.createPutMessageFromCore(request);
        noarkClient.sendEduMelding(putMessage);
        request.setPayload(oldPayload);
        request.setMessageType(EDUCore.MessageType.EDU);
        request.swapSenderAndReceiver();
    }

    private void sendErrorAppReceipt(StandardBusinessDocument sbd) {
        AppReceiptType receipt = new AppReceiptType();
        receipt.setType("ERROR");
        StatusMessageType statusMessageType = new StatusMessageType();
        statusMessageType.setCode("ID");
        statusMessageType.setText(String.format("Feilet under mottak hos %s", sbd.getReceiverOrgNumber()));
        receipt.getMessage().add(statusMessageType);

        EDUCore eduCore = eduCoreFactory.create(receipt,
                sbd.getConversationId(),
                sbd.getReceiverOrgNumber(),
                sbd.getSenderOrgNumber());
        enqueueExternal(eduCore);
    }

    public void enqueueNextMove2(NextMoveMessage msg) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            objectMapper.writeValue(bos, msg);
            jmsTemplate.convertAndSend(NEXTMOVE, bos.toByteArray());
        } catch (IOException e) {
            throw new NextMoveRuntimeException(String.format("Unable to marshall NextMove message with id=%s", msg.getConversationId()), e);
        }
    }

    /**
     * Place the input parameter on external queue. The external queue sends messages to an external recipient using transport
     * mechnism.
     *
     * @param request the input parameter from IntegrasjonspunktImpl
     */
    public void enqueueExternal(EDUCore request) {
        try {
            jmsTemplate.convertAndSend(EXTERNAL, EDUCoreConverter.marshallToBytes(request));
        } catch (Exception e) {
            Audit.error("Unable to send message", EDUCoreMarker.markerFrom(request), e);
            throw e;
        }
    }

    /**
     * Places the input parameter on the NOARK queue. The NOARK queue sends messages from external sender to NOARK server.
     *
     * @param sbd the sbd as received by IntegrasjonspunktReceiveImpl from an external source
     */
    public void enqueueNoark(StandardBusinessDocument sbd) {
        jmsTemplate.convertAndSend(NOARK, documentConverter.marshallToBytes(sbd));
    }

    public void enqueuePutMessage(PutMessageRequestType putMessage) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            marshaller.marshal(new JAXBElement<>(new QName("uri", "local"), PutMessageRequestType.class, putMessage), bos);
            jmsTemplate.convertAndSend(PUTMSG, bos.toByteArray());
        } catch (JAXBException e) {
            Audit.error("Unable to queue putmessage", PutMessageMarker.markerFrom(new PutMessageRequestWrapper(putMessage)), e);
            throw new MeldingsUtvekslingRuntimeException(e);
        }
    }

    public void forwardToNoark(StandardBusinessDocument sbd) {
        try {
            sendToNoarkSystem(sbd);
        } catch (Exception e) {
            Audit.error("Failed to unserialize SBD");
            throw new MeldingsUtvekslingRuntimeException("Could not forward document to archive system", e);
        }
    }

    private void sendToNoarkSystem(StandardBusinessDocument sbd) {
        try {
            integrajonspunktReceive.forwardToNoarkSystem(sbd);
        } catch (Exception e) {
            Audit.error("Failed delivering to archive", markerFrom(sbd), e);
            if (e instanceof MessageException) {
                logger.error(markerFrom(sbd), ((MessageException) e).getStatusMessage().getTechnicalMessage(), e);
            }
            throw new MeldingsUtvekslingRuntimeException(e);
        }
    }

    public void setIntegrajonspunktReceiveImpl(IntegrajonspunktReceiveImpl integrajonspunktReceiveImpl) {
        this.integrajonspunktReceive = integrajonspunktReceiveImpl;
    }
}
