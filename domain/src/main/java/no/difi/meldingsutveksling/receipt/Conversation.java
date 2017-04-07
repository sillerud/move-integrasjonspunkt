package no.difi.meldingsutveksling.receipt;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import no.difi.meldingsutveksling.ServiceIdentifier;
import no.difi.meldingsutveksling.core.EDUCore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
public class Conversation {

    @Id
    @GeneratedValue
    private Integer genId;
    private String conversationId;
    private String receiverIdentifier;
    private String messageReference;
    private String messageTitle;
    private LocalDateTime lastUpdate;
    private boolean pollable;
    private ServiceIdentifier serviceIdentifier;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MessageReceipt> messageReceipts;

    Conversation() {}

    private Conversation(String conversationId,
                         String messageReference,
                         String receiverIdentifier,
                         String messageTitle,
                         ServiceIdentifier serviceIdentifier,
                         List<MessageReceipt> receipts) {
        this.conversationId = conversationId;
        this.messageReference = messageReference;
        this.receiverIdentifier = receiverIdentifier;
        this.messageTitle = messageTitle;
        this.messageReceipts = receipts;
        this.serviceIdentifier = serviceIdentifier;
        this.lastUpdate = LocalDateTime.now();
        switch (serviceIdentifier) {
            case DPV:
            case DPF:
                this.pollable = true;
                break;
            case DPI:
            case DPO:
            default:
                this.pollable = false;
                break;
        }
    }

    public static Conversation of(String conversationId,
                                  String messageReference,
                                  String receiverIdentifier,
                                  String messageTitle,
                                  ServiceIdentifier serviceIdentifier,
                                  MessageReceipt... receipts) {
        if (receipts == null || receipts.length == 0) {
            return new Conversation(conversationId, messageReference, receiverIdentifier, messageTitle,
                    serviceIdentifier, Lists.newArrayList());
        }
        return new Conversation(conversationId, messageReference, receiverIdentifier, messageTitle,
                serviceIdentifier, Arrays.asList(receipts));
    }

    public static Conversation of(EDUCore eduCore, MessageReceipt... receipts) {
        String msgTitle = "";
        if (eduCore.getMessageType() == EDUCore.MessageType.EDU) {
            msgTitle = eduCore.getPayloadAsMeldingType().getJournpost().getJpInnhold();
        }
        return new Conversation(eduCore.getId(), eduCore.getMessageReference(), eduCore.getReceiver().getIdentifier(),
                msgTitle, eduCore.getServiceIdentifier() , Arrays.asList(receipts));
    }

    public Integer getId() {
        return genId;
    }

    public void setId(Integer genId) {
        this.genId = genId;
    }

    public boolean isPollable() {
        return pollable;
    }

    public void setPollable(boolean pollable) {
        this.pollable = pollable;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ServiceIdentifier getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(ServiceIdentifier serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public List<MessageReceipt> getMessageReceipts() {
        return messageReceipts;
    }

    public void addMessageReceipt(MessageReceipt receipt) {
        this.messageReceipts.add(receipt);
        this.lastUpdate = LocalDateTime.now();
    }

    public void setMessageReceipts(List<MessageReceipt> messageReceipts) {
        this.messageReceipts = messageReceipts;
    }

    public String getReceiverIdentifier() {
        return receiverIdentifier;
    }

    public void setReceiverIdentifier(String receiverIdentifier) {
        this.receiverIdentifier = receiverIdentifier;
    }

    public String getMessageReference() {
        return messageReference;
    }

    public void setMessageReference(String messageReference) {
        this.messageReference = messageReference;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("genId", genId)
                .add("conversationId", conversationId)
                .add("messageReference", messageReference)
                .add("receiverIdentifier", receiverIdentifier)
                .add("messageReference", messageReference)
                .add("messageTitle", messageTitle)
                .add("pollable", pollable)
                .add("serviceIdentifier", serviceIdentifier)
                .add("messageReceipts", messageReceipts)
                .toString();
    }
}