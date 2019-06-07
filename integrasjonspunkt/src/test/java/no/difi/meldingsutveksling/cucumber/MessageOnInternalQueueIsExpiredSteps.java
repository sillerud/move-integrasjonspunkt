package no.difi.meldingsutveksling.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.RequiredArgsConstructor;
import no.difi.meldingsutveksling.domain.sbdh.SBDUtil;
import no.difi.meldingsutveksling.nextmove.ConversationDirection;
import no.difi.meldingsutveksling.nextmove.NextMoveOutMessage;
import no.difi.meldingsutveksling.receipt.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MessageOnInternalQueueIsExpiredSteps {

    private final SBDUtil sbdUtil;
    private final NextMoveOutMessage msg;
    private final ConversationService conversationService;
    private final MessageStatusFactory messageStatusFactory;
    private final Conversation conversation;
    private final ConversationRepository repo;

    @Given("^a message exists on the internal queue$")
    public void aMessageExistsOnTheInternalQueue() {
        sbdUtil.isStatus(msg.getSbd());
    }

    @And("^the message is expired$")
    public void theMessageIsExpired() {
        sbdUtil.isExpired(msg.getSbd());
    }

    @And("^the application attempts to send the message$")
    public void theApplicationAttemptsToSendTheMessage() {

    }

    @Then("^error status will be registered and message stopped$")
    public void errorStatusWillBeRegisteredAndMessageStopped() {
        Optional<Conversation> conv = conversationService.findConversation(msg.getConversationId());
        conversationService.registerStatus(msg.getConversationId(), messageStatusFactory.getMessageStatus(ReceiptStatus.LEVETID_UTLOPT));
        conv.ifPresent(conversationService::markFinished);
        // registrer status og setter finsihedflag. Bør vel egentlig settast mot ein mock. Mock av ConversationRepo ?
        // //Må vel sjekkast med assert: Korleis kan ein sjekke at det faktisk er satt som forventa?
        List<Conversation> convList = repo.findByConversationIdAndDirection(msg.getConversationId(), ConversationDirection.OUTGOING);


        // må kanskje inn i messageStatusFactory for å hente det
        // kvifor blir det void som returntype når eg sette ei liste av messagestatus for å lagre objektet?
    }
}
