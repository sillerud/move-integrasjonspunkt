package no.difi.meldingsutveksling.nextmove;

import no.altinn.services.serviceengine.correspondence._2017._02.InsertCorrespondenceAECV2;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.nextmove.logging.ConversationResourceMarkers;
import no.difi.meldingsutveksling.nextmove.message.MessagePersister;
import no.difi.meldingsutveksling.noarkexchange.putmessage.PostVirksomhetStrategyFactory;
import no.difi.meldingsutveksling.ptv.CorrespondenceAgencyClient;
import no.difi.meldingsutveksling.ptv.CorrespondenceAgencyConfiguration;
import no.difi.meldingsutveksling.ptv.CorrespondenceAgencyMessageFactory;
import no.difi.meldingsutveksling.ptv.CorrespondenceRequest;
import no.difi.meldingsutveksling.serviceregistry.ServiceRegistryLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DpvConversationStrategy implements ConversationStrategy {

    private IntegrasjonspunktProperties props;
    private ServiceRegistryLookup sr;
    private MessagePersister messagePersister;

    @Autowired
    DpvConversationStrategy(IntegrasjonspunktProperties props,
                            ServiceRegistryLookup sr,
                            MessagePersister messagePersister) {
        this.props = props;
        this.sr = sr;
        this.messagePersister = messagePersister;
    }

    @Override
    public void send(ConversationResource conversationResource) throws NextMoveException {
        DpvConversationResource cr = (DpvConversationResource) conversationResource;

        PostVirksomhetStrategyFactory dpvFactory = PostVirksomhetStrategyFactory.newInstance(props, null, sr);
        CorrespondenceAgencyConfiguration config = dpvFactory.getConfig();
        InsertCorrespondenceAECV2 message = CorrespondenceAgencyMessageFactory.create(config, cr, messagePersister);

        CorrespondenceAgencyClient client = new CorrespondenceAgencyClient(ConversationResourceMarkers.markerFrom(cr), config);
        final CorrespondenceRequest request = new CorrespondenceRequest.Builder()
                .withPayload(message).build();

        if (client.sendCorrespondence(request) == null) {
            throw new NextMoveException("Failed to create Correspondence Agency Request");
        }

    }

}
