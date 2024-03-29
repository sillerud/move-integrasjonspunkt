package no.difi.meldingsutveksling.noarkexchange.putmessage;

import no.difi.meldingsutveksling.KeystoreProvider;
import no.difi.meldingsutveksling.config.DigitalPostInnbyggerConfig;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.config.KeyStoreProperties;
import no.difi.meldingsutveksling.ks.svarut.SvarUtService;
import no.difi.meldingsutveksling.noarkexchange.MessageSender;
import no.difi.meldingsutveksling.noarkexchange.NoarkClient;
import no.difi.meldingsutveksling.noarkexchange.receive.InternalQueue;
import no.difi.meldingsutveksling.serviceregistry.ServiceRegistryLookup;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.InfoRecord;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static no.difi.meldingsutveksling.ServiceIdentifier.*;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StrategyFactoryTest {

    private StrategyFactory strategyFactory;

    @Before
    public void setup() throws MalformedURLException {
        final MessageSender messageSender = mock(MessageSender.class);
        final IntegrasjonspunktProperties properties = mock(IntegrasjonspunktProperties.class);
        final IntegrasjonspunktProperties.PostVirksomheter ptvMock = mock(IntegrasjonspunktProperties.PostVirksomheter.class);
        final DigitalPostInnbyggerConfig dpic = mock(DigitalPostInnbyggerConfig.class);
        final KeyStoreProperties keystore = mock(KeyStoreProperties.class);
        final IntegrasjonspunktProperties.Organization orgMock = mock(IntegrasjonspunktProperties.Organization.class);
        when(ptvMock.getEndpointUrl()).thenReturn(new URL("http://foo"));
        IntegrasjonspunktProperties.FeatureToggle featureMock = mock(IntegrasjonspunktProperties.FeatureToggle.class);
        IntegrasjonspunktProperties.NextMove nextMoveMock = mock(IntegrasjonspunktProperties.NextMove.class);
        when(nextMoveMock.getFiledir()).thenReturn("upload/");
        when(properties.getNextmove()).thenReturn(nextMoveMock);
        when(featureMock.isEnableDPO()).thenReturn(true);
        when(featureMock.isEnableDPI()).thenReturn(true);
        when(featureMock.isEnableDPV()).thenReturn(true);
        when(properties.getFeature()).thenReturn(featureMock);
        when(dpic.getKeystore()).thenReturn(keystore);
        when(dpic.getFeature()).thenReturn(new DigitalPostInnbyggerConfig.FeatureToggle());
        when(messageSender.getProperties()).thenReturn(properties);
        when(properties.getDpv()).thenReturn(ptvMock);
        when(properties.getDpi()).thenReturn(dpic);
        when(properties.getOrg()).thenReturn(orgMock);
        final ServiceRegistryLookup serviceRegistryLookup = mock(ServiceRegistryLookup.class);
        when(serviceRegistryLookup.getInfoRecord(anyString())).thenReturn(mock(InfoRecord.class));


        final KeystoreProvider keystoreProvider = mock(KeystoreProvider.class);
        SvarUtService svarUtService = mock(SvarUtService.class);
        NoarkClient noarkClientMock = mock(NoarkClient.class);
        InternalQueue internalQueue = mock(InternalQueue.class);
        strategyFactory = new StrategyFactory(messageSender, serviceRegistryLookup, keystoreProvider, properties, noarkClientMock, internalQueue);
        strategyFactory.registerMessageStrategyFactory(FiksMessageStrategyFactory.newInstance(svarUtService, noarkClientMock));

    }

    @Test
    public void givenFiksServiceRecordShouldCreateFIKSMessageStrategyFactory() {
        MessageStrategyFactory factory = strategyFactory.getFactory(DPF);

        assertThat(factory, instanceOf(FiksMessageStrategyFactory.class));
    }

    @Test
    public void givenEduServiceRecordShouldCreateEduMessageStrategyFactory() {
        // when
        final MessageStrategyFactory factory = strategyFactory.getFactory(DPO);
        // then

        assertThat(factory, instanceOf(EduMessageStrategyFactory.class));
    }

    @Test
    public void givenPostServiceRecordShouldCreatePostMessageStrategyFactory() {
        final MessageStrategyFactory factory = strategyFactory.getFactory(DPV);

        assertThat(factory, instanceOf(PostVirksomhetStrategyFactory.class));
    }

    @Test(expected = NullPointerException.class)
    public void emptyServiceRecordThrowsException() {
        strategyFactory.getFactory(null);
    }

}
