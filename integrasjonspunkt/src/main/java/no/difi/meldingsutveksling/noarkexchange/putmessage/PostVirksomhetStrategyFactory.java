package no.difi.meldingsutveksling.noarkexchange.putmessage;

import no.difi.meldingsutveksling.ServiceIdentifier;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.noarkexchange.NoarkClient;
import no.difi.meldingsutveksling.noarkexchange.receive.InternalQueue;
import no.difi.meldingsutveksling.ptv.CorrespondenceAgencyConfiguration;
import no.difi.meldingsutveksling.serviceregistry.ServiceRegistryLookup;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.InfoRecord;

import java.util.Optional;


public class PostVirksomhetStrategyFactory implements MessageStrategyFactory {

    private final CorrespondenceAgencyConfiguration configuration;
    private final NoarkClient noarkClient;
    private final InternalQueue internalQueue;

    private PostVirksomhetStrategyFactory(CorrespondenceAgencyConfiguration configuration,
                                          NoarkClient noarkClient,
                                          InternalQueue internalQueue) {
        this.configuration = configuration;
        this.noarkClient = noarkClient;
        this.internalQueue = internalQueue;
    }

    public static PostVirksomhetStrategyFactory newInstance(IntegrasjonspunktProperties properties,
                                                            NoarkClient noarkClient,
                                                            ServiceRegistryLookup serviceRegistryLookup,
                                                            InternalQueue internalQueue) {

        InfoRecord infoRecord = serviceRegistryLookup.getInfoRecord(properties.getOrg().getNumber());
        CorrespondenceAgencyConfiguration.Builder builder = new CorrespondenceAgencyConfiguration.Builder()
                .withExternalServiceCode(properties.getDpv().getExternalServiceCode())
                .withExternalServiceEditionCode(properties.getDpv().getExternalServiceEditionCode())
                .withPassword(properties.getDpv().getPassword())
                .withSystemUserCode(properties.getDpv().getUsername())
                .withSender(infoRecord.getOrganizationName())
                .withAllowForwarding(properties.getDpv().isAllowForwarding())
                .withNotifyEmail(properties.getDpv().isNotifyEmail())
                .withNotifySms(properties.getDpv().isNotifySms());

        Optional<String> notificationText = Optional.ofNullable(properties.getDpv())
                .map(IntegrasjonspunktProperties.PostVirksomheter::getNotificationText);
        notificationText.ifPresent(builder::withNotificationText);

        builder.withNextmoveFiledir(properties.getNextmove().getFiledir());

        builder.withEndpointUrl(properties.getDpv().getEndpointUrl().toString());

        CorrespondenceAgencyConfiguration config = builder.build();
        return new PostVirksomhetStrategyFactory(config, noarkClient, internalQueue);
    }

    @Override
    public MessageStrategy create(Object payload) {
        return new PostVirksomhetMessageStrategy(configuration, noarkClient, internalQueue);
    }

    @Override
    public ServiceIdentifier getServiceIdentifier() {
        return ServiceIdentifier.DPV;
    }

    public CorrespondenceAgencyConfiguration getConfig() {
        return this.configuration;
    }
}
