package no.difi.meldingsutveksling.serviceregistry;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.difi.meldingsutveksling.serviceregistry.client.RestClient;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.EntityType;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.InfoRecord;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.ServiceRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceRegistryLookupTest {

    private static final String ORGNR = "12345678";
    private static final String ORGNAME = "test";

    @Mock
    private RestClient client;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ServiceRegistryLookup service;
    private ServiceRecord post = new ServiceRecord("POST", "000", "certificate", "http://localhost:6789");
    private ServiceRecord edu = new ServiceRecord("EDU", "000", "certificate", "http://localhost:4567");

    @Before
    public void setup() {
        service = new ServiceRegistryLookup(client);
    }

    @Test
    public void clientThrowsExceptionWithHttpStatusNotFoundShouldReturnsEmptyServiceRecord() {
        final String badOrgnr = "-100";
        when(client.getResource("identifier/" + badOrgnr)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        final ServiceRecord serviceRecord = service.getServiceRecord(badOrgnr);

        assertThat(serviceRecord, is(ServiceRecord.EMPTY));
    }

    @Test
    public void clientThrowsExceptionWithInternalServerErrorThenServiceShouldThrowServiceRegistryLookupException() {
        thrown.expect(UncheckedExecutionException.class);
        when(client.getResource(any(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        service.getServiceRecord(ORGNR);
    }

    @Test
    public void organizationWithoutServiceRecord() {
        final String json = new SRContentBuilder().build();
        when(client.getResource("identifier/" + ORGNR)).thenReturn(json);

        final ServiceRecord serviceRecord = this.service.getServiceRecord(ORGNR);

        assertThat(serviceRecord, is(ServiceRecord.EMPTY));
    }

    @Test
    public void organizationWithSingleServiceRecordHasServiceRecord() {
        final String json = new SRContentBuilder().withServiceRecord(edu).build();
        when(client.getResource("identifier/" + ORGNR)).thenReturn(json);

        final ServiceRecord serviceRecord = service.getServiceRecord(ORGNR);

        assertThat(serviceRecord, is(edu));
    }

    public static class SRContentBuilder {
        private Gson gson = new GsonBuilder().serializeNulls().create();
        private ServiceRecord serviceRecord;

        SRContentBuilder withServiceRecord(ServiceRecord serviceRecord) {
            this.serviceRecord = serviceRecord;
            return this;
        }

        String build() {
            EntityType entityType = new EntityType("Organisasjonsledd", "ORGL");
            InfoRecord infoRecord = new InfoRecord(ORGNR, ORGNAME, entityType);

            final HashMap<String, Object> content = new HashMap<>();

            if (this.serviceRecord == null) {
                content.put("serviceRecord", ServiceRecord.EMPTY);
            } else {
                content.put("serviceRecord", this.serviceRecord);
            }
            content.put("infoRecord", infoRecord);
            return gson.toJson(content);
        }

    }
}