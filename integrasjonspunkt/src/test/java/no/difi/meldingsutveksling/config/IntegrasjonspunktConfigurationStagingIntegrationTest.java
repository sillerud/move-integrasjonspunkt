package no.difi.meldingsutveksling.config;

import no.difi.meldingsutveksling.domain.MeldingsUtvekslingRequiredPropertyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegrasjonspunktConfiguration.class)
@ActiveProfiles("staging")
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class IntegrasjonspunktConfigurationStagingIntegrationTest {
    @Autowired
    private Environment environment;

    private IntegrasjonspunktConfiguration configuration;

    @Before
    public void setUp() throws MeldingsUtvekslingRequiredPropertyException {
        configuration = new IntegrasjonspunktConfiguration(environment);
    }

    @Test
    public void shouldHaveStagingProfileWhenNoProfileIsGiven() throws MeldingsUtvekslingRequiredPropertyException {
        assertEquals("staging", configuration.getProfile());
    }

    @Test
    public void shouldHaveEndpointsEnabledWhenStagingEnvironment() {
        assertEquals("false", environment.getProperty("endpoints.enabled"));
    }

    @Test
    public void shouldHaveEnabledSecurityWhenStagingEnvironment() {
        assertEquals("true", environment.getProperty("security.basic.enabled"));
    }

    @Test
    public void shouldHaveDisabledHealthSensitiveInformationWhenStagingEnvironment() {
        assertEquals("false", environment.getProperty("endpoints.health.sensitive"));
    }

    @Test
    public void shouldHaveEnabledQueueFeatureToggleWhenStagingEnvironment() {
        assertEquals(true, configuration.isQueueEnabled());
    }

    @Test
    public void shouldHaveOrganizationNumberWhenStagingEnvironment() {
        assertEquals(false, configuration.hasOrganisationNumber());
    }

    @Test
    public void shouldHaveStagingAltinnUsernameDefaultWhenStagingEnvironment() {
        assertNotNull(configuration.getAltinnUsername());
    }

    @Test
    public void shouldHaveStagingAltinnPasswordDefaultWhenStagingEnvironment() {
        assertNotNull(configuration.getAltinnPassword());
    }

    @Test
    public void shouldHaveAltinnExternalServiceCodeForTestWhenStagingEnvironment() {
        assertEquals("4192", configuration.getAltinnServiceCode());
    }

    @Test
    public void shouldHaveAltinnExternalServiceEditionCodeForTestWhenStagingEnvironment() {
        assertEquals("270815", configuration.getAltinnServiceEditionCode());
    }
}
