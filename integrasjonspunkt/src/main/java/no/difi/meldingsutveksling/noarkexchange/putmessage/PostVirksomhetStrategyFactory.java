package no.difi.meldingsutveksling.noarkexchange.putmessage;

import lombok.extern.slf4j.Slf4j;
import no.difi.meldingsutveksling.KeystoreProvider;
import no.difi.meldingsutveksling.ServiceIdentifier;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.domain.MeldingsUtvekslingRuntimeException;
import no.difi.meldingsutveksling.lang.KeystoreProviderException;
import no.difi.meldingsutveksling.noarkexchange.NoarkClient;
import no.difi.meldingsutveksling.ptv.CorrespondenceAgencyConfiguration;
import no.difi.meldingsutveksling.serviceregistry.ServiceRegistryLookup;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.InfoRecord;

import java.security.KeyStore;
import java.util.Optional;

@Slf4j
public class PostVirksomhetStrategyFactory implements MessageStrategyFactory {

    private final CorrespondenceAgencyConfiguration configuration;
    private final NoarkClient noarkClient;

    private PostVirksomhetStrategyFactory(CorrespondenceAgencyConfiguration configuration, NoarkClient noarkClient) {
        this.configuration = configuration;
        this.noarkClient = noarkClient;
    }

    public static PostVirksomhetStrategyFactory newInstance(IntegrasjonspunktProperties properties,
                                                            NoarkClient noarkClient,
                                                            ServiceRegistryLookup serviceRegistryLookup) {

        InfoRecord infoRecord = serviceRegistryLookup.getInfoRecord(properties.getOrg().getNumber());
        CorrespondenceAgencyConfiguration.CorrespondenceAgencyConfigurationBuilder builder = CorrespondenceAgencyConfiguration.builder()
                .externalServiceCode(properties.getDpv().getExternalServiceCode())
                .externalServiceEditionCode(properties.getDpv().getExternalServiceEditionCode())
                .sender(infoRecord.getOrganizationName())
                .notifyEmail(properties.getDpv().isNotifyEmail())
                .notifySms(properties.getDpv().isNotifySms())
                .nextmoveFiledir(properties.getNextmove().getFiledir())
                .endpointUrl(properties.getDpv().getEndpointUrl().toString());

        Optional<String> notificationText = Optional.ofNullable(properties.getDpv())
                .map(IntegrasjonspunktProperties.PostVirksomheter::getNotificationText);
        notificationText.ifPresent(builder::notificationText);

        try {
            KeyStore keyStore = KeystoreProvider.loadKeyStore(properties.getDpv().getKeystore());
            builder.keyStore(keyStore);
        } catch (KeystoreProviderException e) {
            log.error("Error loading DPV keystore", e);
            throw new MeldingsUtvekslingRuntimeException(e);
        }

        CorrespondenceAgencyConfiguration config = builder.build();
        return new PostVirksomhetStrategyFactory(config, noarkClient);
    }

    @Override
    public MessageStrategy create(Object payload) {
        return new PostVirksomhetMessageStrategy(configuration, noarkClient);
    }

    @Override
    public ServiceIdentifier getServiceIdentifier() {
        return ServiceIdentifier.DPV;
    }

    public CorrespondenceAgencyConfiguration getConfig() {
        return this.configuration;
    }
}
