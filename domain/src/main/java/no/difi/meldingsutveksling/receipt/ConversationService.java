package no.difi.meldingsutveksling.receipt;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import no.difi.meldingsutveksling.ServiceIdentifier;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.core.EDUCore;
import no.difi.meldingsutveksling.domain.sbdh.EduDocument;
import no.difi.meldingsutveksling.mail.MailSender;
import no.difi.meldingsutveksling.nextmove.ConversationDirection;
import no.difi.meldingsutveksling.nextmove.ConversationResource;
import no.difi.meldingsutveksling.noarkexchange.NoarkClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;
import static no.difi.meldingsutveksling.ServiceIdentifier.*;
import static no.difi.meldingsutveksling.nextmove.ConversationDirection.INCOMING;
import static no.difi.meldingsutveksling.receipt.GenericReceiptStatus.FEIL;

@Component
@Slf4j
public class ConversationService {

    private ConversationRepository repo;
    private IntegrasjonspunktProperties props;
    private NoarkClient mshClient;
    private MailSender mailSender;

    private static final String CONVERSATION_EXISTS = "Conversation with id=%s already exists, not recreating";
    private static final List<ServiceIdentifier> POLLABLES = Lists.newArrayList(DPV, DPF, DPO);

    @Autowired
    public ConversationService(ConversationRepository repo,
                               IntegrasjonspunktProperties props,
                               @Qualifier("mshClient") ObjectProvider<NoarkClient> mshClient,
                               MailSender mailSender) {
        this.repo = repo;
        this.props = props;
        this.mshClient = mshClient.getIfAvailable();
        this.mailSender = mailSender;
    }

    public Optional<Conversation> registerStatus(String conversationId, MessageStatus status) {
        Optional<Conversation> c = repo.findByConversationId(conversationId).stream().findFirst();
        if (c.isPresent()) {
            return Optional.of(registerStatus(c.get(), status));
        } else {
            log.warn(format("Conversation with id=%s not found, cannot register receipt status=%s", conversationId, status));
            return Optional.empty();
        }
    }

    @SuppressWarnings("squid:S2250")
    public Conversation registerStatus(Conversation conversation, MessageStatus status) {
        boolean hasStatus = conversation.getMessageStatuses().stream()
                .map(MessageStatus::getStatus)
                .anyMatch(status.getStatus()::equals);
        if (!hasStatus) {
            conversation.addMessageStatus(status);

            if (status.getStatus().equals(FEIL.toString()) &&
                    props.getFeature().isMailErrorStatus()) {
                try {
                    String title = format("Integrasjonspunkt: status %s registrert for forsendelse %s", FEIL.toString(), conversation.getConversationId());
                    title = (isNullOrEmpty(conversation.getMessageReference())) ? title : title + format(" / %s", conversation.getMessageReference());
                    String direction = conversation.getDirection() == INCOMING ? "Innkommende" : "Utgående";
                    String messageRef = (isNullOrEmpty(conversation.getMessageReference())) ? "" : format("og messageReference %s ", conversation.getMessageReference());
                    String body = format("%s forsendelse med conversationId %s %shar registrert status '%s'. Se statusgrensesnitt for detaljer.",
                            direction, conversation.getConversationId(), messageRef, FEIL.toString());
                    mailSender.send(title, body);
                } catch (Exception e) {
                    log.error(format("Error sending status mail for conversationId %s", conversation.getConversationId()), e);
                }
            }

            if (conversation.getDirection() == ConversationDirection.OUTGOING &&
                    GenericReceiptStatus.SENDT.toString().equals(status.getStatus()) &&
                    POLLABLES.contains(conversation.getServiceIdentifier()) &&
                    !conversation.isMsh()) {
                conversation.setPollable(true);
            }
            return repo.save(conversation);
        }
        return conversation;
    }

    public Conversation markFinished(Conversation conversation) {
        conversation.setFinished(true);
        conversation.setPollable(false);
        return repo.save(conversation);
    }

    public Conversation registerConversation(EDUCore message) {
        Optional<Conversation> find = repo.findByConversationId(message.getId()).stream().findFirst();
        if (find.isPresent()) {
            log.warn(format(CONVERSATION_EXISTS, message.getId()));
            return find.get();
        }

        MessageStatus ms = MessageStatus.of(GenericReceiptStatus.OPPRETTET);
        if (message.getMessageType() == EDUCore.MessageType.APPRECEIPT) {
            ms.setDescription("AppReceipt");
        }
        Conversation conversation = Conversation.of(message, ms);

        if (!isNullOrEmpty(props.getMsh().getEndpointURL())
                && mshClient.canRecieveMessage(message.getReceiver().getIdentifier())) {
            conversation.setMsh(true);
        }

        return repo.save(conversation);
    }

    public Conversation registerConversation(ConversationResource cr) {
        Optional<Conversation> find = repo.findByConversationId(cr.getConversationId()).stream().findFirst();
        if (find.isPresent()) {
            log.warn(format(CONVERSATION_EXISTS, cr.getConversationId()));
            return find.get();
        }
        MessageStatus ms = MessageStatus.of(GenericReceiptStatus.OPPRETTET);
        Conversation c = Conversation.of(cr, ms);
        return repo.save(c);
    }

    public Conversation registerConversation(EduDocument eduDocument) {
        Optional<Conversation> find = repo.findByConversationId(eduDocument.getConversationId()).stream().findFirst();
        if (find.isPresent()) {
            log.warn(format(CONVERSATION_EXISTS, eduDocument.getConversationId()));
            return find.get();
        }

        MessageStatus ms = MessageStatus.of(GenericReceiptStatus.OPPRETTET);
        Conversation c = Conversation.of(eduDocument, ms);
        return repo.save(c);
    }

    public void setServiceIdentifier(String conversationId, ServiceIdentifier si) {
        Optional<Conversation> find = repo.findByConversationId(conversationId).stream().findFirst();
        if (find.isPresent()) {
            Conversation c = find.get();
            c.setServiceIdentifier(si);
            repo.save(c);
        }
    }

    public void setPollable(String conversationId, boolean pollable) {
        Optional<Conversation> find = repo.findByConversationId(conversationId).stream().findFirst();
        if (find.isPresent()) {
            Conversation c = find.get();
            c.setPollable(pollable);
            repo.save(c);
        }
    }
}
