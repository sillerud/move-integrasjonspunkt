package no.difi.meldingsutveksling.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.RequiredArgsConstructor;
import no.difi.meldingsutveksling.domain.sbdh.SBDUtil;
import no.difi.meldingsutveksling.nextmove.NextMoveOutMessage;
import no.difi.meldingsutveksling.receipt.*;
import org.junit.Before;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public class MessageOnInternalQueueIsExpiredSteps {

    private final SBDUtil sbdUtil;
    private final ConversationService conversationService;
    private final MessageStatusFactory messageStatusFactory;
    private final MessageStatusRepository messageStatusRepository;

    private final ConversationRepository repo;

    NextMoveOutMessage msg;
    Conversation conversation;
    @Before
    public void setUp() {
         msg = new NextMoveOutMessage();
         conversation = new Conversation();
    }


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
        assertTrue(conv.get().getMessageStatuses().stream().anyMatch(s -> ReceiptStatus.LEVETID_UTLOPT.toString().equals(s.getStatus())));
    }
}
