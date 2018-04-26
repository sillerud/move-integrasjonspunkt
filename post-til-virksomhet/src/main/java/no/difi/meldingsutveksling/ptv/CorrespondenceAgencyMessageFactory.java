package no.difi.meldingsutveksling.ptv;

import com.google.common.collect.Lists;
import no.altinn.schemas.serviceengine.formsengine._2009._10.TransportType;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.AttachmentsV2;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.ExternalContentV2;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.InsertCorrespondenceV2;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.ObjectFactory;
import no.altinn.schemas.services.serviceengine.correspondence._2016._02.CorrespondenceStatusFilterV3;
import no.altinn.schemas.services.serviceengine.notification._2009._10.*;
import no.altinn.schemas.services.serviceengine.subscription._2009._10.AttachmentFunctionType;
import no.altinn.services.serviceengine.correspondence._2017._02.GetCorrespondenceStatusDetailsAECV3;
import no.altinn.services.serviceengine.correspondence._2017._02.InsertCorrespondenceAECV2;
import no.altinn.services.serviceengine.reporteeelementlist._2010._10.BinaryAttachmentExternalBEV2List;
import no.altinn.services.serviceengine.reporteeelementlist._2010._10.BinaryAttachmentV2;
import no.difi.meldingsutveksling.core.EDUCore;
import no.difi.meldingsutveksling.core.EDUCoreConverter;
import no.difi.meldingsutveksling.nextmove.DpvConversationResource;
import no.difi.meldingsutveksling.nextmove.NextMoveException;
import no.difi.meldingsutveksling.nextmove.message.MessagePersister;
import no.difi.meldingsutveksling.noarkexchange.schema.core.MeldingType;
import no.difi.meldingsutveksling.receipt.Conversation;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Class used to create an InsertCorrespondenceV2 object based on an internal message format.
 */
public class CorrespondenceAgencyMessageFactory {

    private static final Map<Integer, String> serviceEditionMapping= new HashMap<>();

    static {
        serviceEditionMapping.put(1, "Plan, bygg og geodata");
        serviceEditionMapping.put(2, "Helse, sosial og omsorg");
        serviceEditionMapping.put(3, "Oppvekst og utdanning");
        serviceEditionMapping.put(4, "Kultur, idrett og fritid");
        serviceEditionMapping.put(5, "Trafikk, reiser og samferdsel");
        serviceEditionMapping.put(6, "Natur og miljø");
        serviceEditionMapping.put(7, "Næringsutvikling");
        serviceEditionMapping.put(8, "Skatter og avgifter");
        serviceEditionMapping.put(9, "Tekniske tjenester");
        serviceEditionMapping.put(10, "Administrasjon");
    }

    private CorrespondenceAgencyMessageFactory() {
    }
    
    public static InsertCorrespondenceAECV2 create(CorrespondenceAgencyConfiguration config,
                                                DpvConversationResource cr,
                                                MessagePersister persister) throws NextMoveException {

        no.altinn.services.serviceengine.reporteeelementlist._2010._10.ObjectFactory reporteeFactory = new no.altinn.services.serviceengine.reporteeelementlist._2010._10.ObjectFactory();
        BinaryAttachmentExternalBEV2List attachmentExternalBEV2List = new BinaryAttachmentExternalBEV2List();

        for (String f : cr.getFileRefs().values()) {
            byte[] bytes;
            try {
                bytes = persister.read(cr, f);
            } catch (IOException e) {
                throw new NextMoveException(String.format("Could not read file \"%s\"", f), e);
            }

            BinaryAttachmentV2 binaryAttachmentV2 = new BinaryAttachmentV2();
            binaryAttachmentV2.setFunctionType(AttachmentFunctionType.fromValue("Unspecified"));
            binaryAttachmentV2.setFileName(reporteeFactory.createBinaryAttachmentV2FileName(f));
            binaryAttachmentV2.setName(reporteeFactory.createBinaryAttachmentV2Name(f));
            binaryAttachmentV2.setEncrypted(false);
            binaryAttachmentV2.setSendersReference(reporteeFactory.createBinaryAttachmentV2SendersReference("AttachmentReference_as123452"));
            binaryAttachmentV2.setData(reporteeFactory.createBinaryAttachmentV2Data(bytes));
            attachmentExternalBEV2List.getBinaryAttachmentV2().add(binaryAttachmentV2);
        }

        return create(config, cr.getConversationId(), cr.getReceiverId(), cr.getMessageTitle(),
                cr.getMessageContent(), attachmentExternalBEV2List);
    }

    public static InsertCorrespondenceAECV2 create(CorrespondenceAgencyConfiguration config, EDUCore edu) {

        no.altinn.services.serviceengine.reporteeelementlist._2010._10.ObjectFactory reporteeFactory = new no.altinn.services.serviceengine.reporteeelementlist._2010._10.ObjectFactory();
        BinaryAttachmentExternalBEV2List attachmentExternalBEV2List = new BinaryAttachmentExternalBEV2List();
        MeldingType meldingType = EDUCoreConverter.payloadAsMeldingType(edu.getPayload());
        meldingType.getJournpost().getDokument().forEach(d -> {
            BinaryAttachmentV2 binaryAttachmentV2 = new BinaryAttachmentV2();
            binaryAttachmentV2.setFunctionType(AttachmentFunctionType.fromValue("Unspecified"));
            binaryAttachmentV2.setFileName(reporteeFactory.createBinaryAttachmentV2FileName(d.getVeFilnavn()));
            binaryAttachmentV2.setName(reporteeFactory.createBinaryAttachmentV2Name(d.getVeFilnavn()));
            binaryAttachmentV2.setEncrypted(false);
            binaryAttachmentV2.setSendersReference(reporteeFactory.createBinaryAttachmentV2SendersReference("AttachmentReference_as123452"));
            binaryAttachmentV2.setData(reporteeFactory.createBinaryAttachmentV2Data(d.getFil().getBase64()));
            attachmentExternalBEV2List.getBinaryAttachmentV2().add(binaryAttachmentV2);
        });
        String title = meldingType.getJournpost().getJpInnhold();
        String content = meldingType.getJournpost().getJpOffinnhold();

        return create(config, edu.getId(), edu.getReceiver().getIdentifier(), title, content, attachmentExternalBEV2List);
    }

    public static InsertCorrespondenceAECV2 create(CorrespondenceAgencyConfiguration config,
                                                String conversationId,
                                                String receiverIdentifier,
                                                String messageTitle,
                                                String messageContent,
                                                BinaryAttachmentExternalBEV2List attachments) {

        InsertCorrespondenceV2 correspondence = new InsertCorrespondenceV2();
        ObjectFactory objectFactory = new ObjectFactory();

        correspondence.setReportee(objectFactory.createInsertCorrespondenceV2Reportee(receiverIdentifier));
        // Service code, default 4255
        correspondence.setServiceCode(getServiceCode(config));
        // Service edition, default 10
        correspondence.setServiceEdition(getServiceEditionCode(config));
        // Should the user be allowed to forward the message from portal
        correspondence.setAllowForwarding(objectFactory.createInsertCorrespondenceV2AllowForwarding(false));
        // Name of the message sender, always "Avsender"
        correspondence.setMessageSender(objectFactory.createInsertCorrespondenceV2MessageSender(config.getSender()));
        // The date and time the message should be visible in the Portal
        correspondence.setVisibleDateTime(toXmlGregorianCalendar(ZonedDateTime.now()));
        correspondence.setDueDateTime(toXmlGregorianCalendar(ZonedDateTime.now().plusDays(7)));

        ExternalContentV2 externalContentV2 = new ExternalContentV2();
        externalContentV2.setLanguageCode(objectFactory.createExternalContentV2LanguageCode("1044"));
        externalContentV2.setMessageTitle(objectFactory.createExternalContentV2MessageTitle(messageTitle));
        externalContentV2.setMessageSummary(objectFactory.createExternalContentV2MessageSummary(messageTitle));
        externalContentV2.setMessageBody(objectFactory.createExternalContentV2MessageBody(messageContent));

        // The date and time the message can be deleted by the user
        correspondence.setAllowSystemDeleteDateTime(
                objectFactory.createInsertCorrespondenceV2AllowSystemDeleteDateTime(
                        toXmlGregorianCalendar(getAllowSystemDeleteDateTime())));



        AttachmentsV2 attachmentsV2 = new AttachmentsV2();
        attachmentsV2.setBinaryAttachments(objectFactory.createAttachmentsV2BinaryAttachments(attachments));
        externalContentV2.setAttachments(objectFactory.createExternalContentV2Attachments(attachmentsV2));
        correspondence.setContent(objectFactory.createInsertCorrespondenceV2Content(externalContentV2));

        List<Notification2009> notificationList = createNotifications(config);

        NotificationBEList notifications = new NotificationBEList();
        List<Notification2009> notification = notifications.getNotification();
        notification.addAll(notificationList);
        correspondence.setNotifications(objectFactory.createInsertCorrespondenceV2Notifications(notifications));

        no.altinn.services.serviceengine.correspondence._2017._02.ObjectFactory corresondenceObjectFactory = new no.altinn.services.serviceengine.correspondence._2017._02.ObjectFactory();
        InsertCorrespondenceAECV2 insertCorrespondenceAECV2 = corresondenceObjectFactory.createInsertCorrespondenceAECV2();
        insertCorrespondenceAECV2.setCorrespondence(correspondence);
        // Reference set by the message sender
        insertCorrespondenceAECV2.setExternalShipmentReference(conversationId);

        return insertCorrespondenceAECV2;
    }

    private static List<Notification2009> createNotifications(CorrespondenceAgencyConfiguration config) {

        List<Notification2009> notifications = Lists.newArrayList();

        if (config.isNotifyEmail() && config.isNotifySms()) {
            notifications.add(createNotification(config, TransportType.BOTH));
        } else if (config.isNotifySms()) {
            notifications.add(createNotification(config, TransportType.SMS));
        } else if (config.isNotifyEmail()){
            notifications.add(createNotification(config, TransportType.EMAIL));
        }

        return notifications;
    }

    private static Notification2009 createNotification(CorrespondenceAgencyConfiguration config, TransportType type) {

        Notification2009 notification = new Notification2009();
        no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory notificationFactory = new no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory();
        notification.setFromAddress(notificationFactory.createNotification2009FromAddress("no-reply@altinn.no"));
        // The date and time the notification should be sent
        notification.setShipmentDateTime(toXmlGregorianCalendar(ZonedDateTime.now().plusMinutes(5)));
        // Language code of the notification
        notification.setLanguageCode(notificationFactory.createNotification2009LanguageCode("1044"));
        // Notification type
        notification.setNotificationType(notificationFactory.createNotification2009NotificationType("VarselDPVMedRevarsel"));
        notification.setTextTokens(notificationFactory.createNotification2009TextTokens(createTokens(config)));
        JAXBElement<ReceiverEndPointBEList> receiverEndpoints = createReceiverEndPoint(type);
        notification.setReceiverEndPoints(receiverEndpoints);

        return notification;
    }

    private static TextTokenSubstitutionBEList createTokens(CorrespondenceAgencyConfiguration config) {

        TextTokenSubstitutionBEList tokens = new TextTokenSubstitutionBEList();
        if (!isNullOrEmpty(config.getNotificationText())) {
            tokens.getTextToken().add(createTextToken(1, config.getNotificationText()));
        } else {
            tokens.getTextToken().add(createTextToken(1, String.format("Du har mottatt en melding fra %s.", config.getSender())));
        }

        return tokens;
    }

    private static ZonedDateTime getAllowSystemDeleteDateTime() {
        return ZonedDateTime.now().plusMinutes(5);
    }


    public static GetCorrespondenceStatusDetailsAECV3 createReceiptRequest(Conversation conversation) {

        no.altinn.services.serviceengine.correspondence._2017._02.ObjectFactory of = new no.altinn.services.serviceengine.correspondence._2017._02.ObjectFactory();

        GetCorrespondenceStatusDetailsAECV3 statusRequest = of.createGetCorrespondenceStatusDetailsAECV3();

        CorrespondenceStatusFilterV3 filter = new CorrespondenceStatusFilterV3();
        no.altinn.schemas.services.serviceengine.correspondence._2016._02.ObjectFactory of201602 = new no.altinn.schemas.services.serviceengine.correspondence._2016._02.ObjectFactory();
        JAXBElement<String> sendersReference = of201602.createCorrespondenceStatusFilterV3SendersReference(conversation.getConversationId());
        filter.setSendersReference(sendersReference);
        filter.setServiceCode("4255");
        filter.setServiceEditionCode(10);
        statusRequest.setFilterCriteria(filter);

        return statusRequest;
    }

    private static JAXBElement<String> getServiceCode(CorrespondenceAgencyConfiguration postConfig) {
        String serviceCodeProp= postConfig.getExternalServiceCode();
        String serviceCode= !isNullOrEmpty(serviceCodeProp) ? serviceCodeProp : "4255";
        ObjectFactory objectFactory = new ObjectFactory();
        return objectFactory.createInsertCorrespondenceV2ServiceCode(serviceCode);
    }

    private static JAXBElement<String> getServiceEditionCode(CorrespondenceAgencyConfiguration postConfig) {
        String serviceEditionProp = postConfig.getExternalServiceEditionCode();
        String serviceEdition = !isNullOrEmpty(serviceEditionProp) ? serviceEditionProp : "10";
        ObjectFactory objectFactory = new ObjectFactory();
        return objectFactory.createInsertCorrespondenceV2ServiceEdition(serviceEdition);
    }

    private static TextToken createTextToken(int num, String value) {
        no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory objectFactory = new no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory();
        TextToken textToken = new TextToken();
        textToken.setTokenNum(num);
        textToken.setTokenValue(objectFactory.createTextTokenTokenValue(value));

        return textToken;
    }

    private static JAXBElement<ReceiverEndPointBEList> createReceiverEndPoint(TransportType type) {
        no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory objectFactory = new no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory();
        ReceiverEndPoint receiverEndPoint = new ReceiverEndPoint();
        receiverEndPoint.setTransportType(objectFactory.createReceiverEndPointTransportType(type));
        ReceiverEndPointBEList receiverEndpoints = new ReceiverEndPointBEList();
        receiverEndpoints.getReceiverEndPoint().add(receiverEndPoint);
        return objectFactory.createNotification2009ReceiverEndPoints(receiverEndpoints);
    }

    private static XMLGregorianCalendar toXmlGregorianCalendar(ZonedDateTime date) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar.from(date));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Could not convert ZonedDateTime(value="+date+") to " + XMLGregorianCalendar.class, e);
        }
    }

}
