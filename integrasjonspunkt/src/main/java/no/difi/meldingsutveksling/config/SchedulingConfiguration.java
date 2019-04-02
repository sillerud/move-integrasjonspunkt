package no.difi.meldingsutveksling.config;

import no.difi.meldingsutveksling.AltinnWsClientFactory;
import no.difi.meldingsutveksling.ApplicationContextHolder;
import no.difi.meldingsutveksling.IntegrasjonspunktNokkel;
import no.difi.meldingsutveksling.ks.svarinn.SvarInnService;
import no.difi.meldingsutveksling.nextmove.ConversationResourceRepository;
import no.difi.meldingsutveksling.nextmove.ConversationResourceUnlocker;
import no.difi.meldingsutveksling.nextmove.NextMoveQueue;
import no.difi.meldingsutveksling.nextmove.NextMoveServiceBus;
import no.difi.meldingsutveksling.nextmove.message.MessagePersister;
import no.difi.meldingsutveksling.noarkexchange.altinn.MessagePolling;
import no.difi.meldingsutveksling.noarkexchange.altinn.SvarInnEduCoreForwarder;
import no.difi.meldingsutveksling.noarkexchange.altinn.SvarInnNextMoveForwarder;
import no.difi.meldingsutveksling.noarkexchange.receive.InternalQueue;
import no.difi.meldingsutveksling.receipt.ConversationService;
import no.difi.meldingsutveksling.serviceregistry.ServiceRegistryLookup;
import no.difi.meldingsutveksling.transport.TransportFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    @Bean
    public ConversationResourceUnlocker conversationResourceUnlocker(ConversationResourceRepository repo) {
        return new ConversationResourceUnlocker(repo);
    }

    @Bean
    public MessagePolling messagePolling(
            IntegrasjonspunktProperties properties,
            InternalQueue internalQueue,
            IntegrasjonspunktNokkel keyInfo,
            TransportFactory transportFactory,
            ServiceRegistryLookup serviceRegistryLookup,
            ConversationService conversationService,
            NextMoveQueue nextMoveQueue,
            NextMoveServiceBus nextMoveServiceBus,
            ObjectProvider<MessagePersister> messagePersister,
            AltinnWsClientFactory altinnWsClientFactory,
            SvarInnService svarInnService,
            SvarInnEduCoreForwarder svarInnEduCoreForwarder,
            SvarInnNextMoveForwarder svarInnNextMoveForwarder,
            ApplicationContextHolder applicationContextHolder) {
        return new MessagePolling(
                properties,
                internalQueue,
                keyInfo,
                transportFactory,
                serviceRegistryLookup,
                conversationService,
                nextMoveQueue,
                nextMoveServiceBus,
                messagePersister.getIfUnique(),
                altinnWsClientFactory,
                svarInnService,
                svarInnEduCoreForwarder,
                svarInnNextMoveForwarder,
                applicationContextHolder);
    }
}
