package no.difi.meldingsutveksling.serviceregistry;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.nimbusds.jose.proc.BadJWSException;
import lombok.extern.slf4j.Slf4j;
import no.difi.meldingsutveksling.ServiceIdentifier;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.serviceregistry.client.RestClient;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.InfoRecord;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.Notification;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.ServiceRecord;
import no.difi.meldingsutveksling.serviceregistry.externalmodel.ServiceRecordWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.difi.meldingsutveksling.serviceregistry.externalmodel.ServiceRecord.isServiceIdentifier;

@Service
@Slf4j
public class ServiceRegistryLookup {

    private final RestClient client;
    private IntegrasjonspunktProperties properties;
    private SasKeyRepository sasKeyRepository;
    private final LoadingCache<String, String> skCache;
    private final LoadingCache<Parameters, ServiceRecordWrapper> srCache;
    private final LoadingCache<Parameters, List<ServiceRecord>> srsCache;
    private final LoadingCache<Parameters, InfoRecord> irCache;

    @Autowired
    public ServiceRegistryLookup(RestClient client,
                                 IntegrasjonspunktProperties properties,
                                 SasKeyRepository sasKeyRepository) {
        this.client = client;
        this.properties = properties;
        this.sasKeyRepository = sasKeyRepository;

        this.skCache = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return loadSasKey();
                    }
                });

        this.srCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<Parameters, ServiceRecordWrapper>() {
                    @Override
                    public ServiceRecordWrapper load(Parameters key) throws Exception {
                        return loadServiceRecord(key);
                    }
                });

        this.srsCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<Parameters, List<ServiceRecord>>() {
                    @Override
                    public List<ServiceRecord> load(Parameters key) throws Exception {
                        return loadServiceRecords(key);
                    }
                });

        this.irCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<Parameters, InfoRecord>() {
                    @Override
                    public InfoRecord load(Parameters key) throws Exception {
                        return loadInfoRecord(key);
                    }
                });
    }

    /**
     * Method to find out which transport channel to use to send messages to given organization
     * @param identifier of the receiver
     * @return a ServiceRecord if found. Otherwise an empty ServiceRecord is returned.
     */
    public ServiceRecordWrapper getServiceRecord(String identifier) {
        Notification notification = properties.isVarslingsplikt()? Notification.OBLIGATED : Notification.NOT_OBLIGATED;
        return srCache.getUnchecked(new Parameters(identifier, notification));
    }

    public Optional<ServiceRecord> getServiceRecord(String identifier, ServiceIdentifier serviceIdentifier) {
        Notification notification = properties.isVarslingsplikt()? Notification.OBLIGATED : Notification.NOT_OBLIGATED;
        List<ServiceRecord> serviceRecords = srsCache.getUnchecked(new Parameters(identifier, notification));
        return serviceRecords.stream().filter(isServiceIdentifier(serviceIdentifier)).findFirst();
    }

    public List<ServiceRecord> getServiceRecords(String identifier) {
        Notification notification = properties.isVarslingsplikt()? Notification.OBLIGATED : Notification.NOT_OBLIGATED;
        return srsCache.getUnchecked(new Parameters(identifier, notification));
    }

    private ServiceRecordWrapper loadServiceRecord(Parameters parameters) {
        ServiceRecord serviceRecord;
        ServiceRecordWrapper recordWrapper;
        try {
            final String serviceRecords = client.getResource("identifier/" + parameters.getIdentifier(), parameters.getQuery());
            final DocumentContext documentContext = JsonPath.parse(serviceRecords, jsonPathConfiguration());
            serviceRecord = documentContext.read("$.serviceRecord", ServiceRecord.class);
            String[] failedServiceIdentifiers = documentContext.read("$.failedServiceIdentifiers", String[].class);
            Map<ServiceIdentifier, Integer> securitylevels = documentContext.read("$.securitylevels", new TypeRef<Map<ServiceIdentifier, Integer>>() {});
            recordWrapper = ServiceRecordWrapper.of(serviceRecord, Stream.of(failedServiceIdentifiers).map(ServiceIdentifier::valueOf).collect(Collectors.toList()), securitylevels);
        } catch(HttpClientErrorException httpException) {
            if (Arrays.asList(HttpStatus.NOT_FOUND, HttpStatus.UNAUTHORIZED).contains(httpException.getStatusCode())) {
                log.warn("RestClient returned {} when looking up service record with identifier {}",
                        httpException.getStatusCode(), parameters, httpException);
                throw httpException;
            } else {
                throw new ServiceRegistryLookupException(String.format("RestClient threw exception when looking up service record with identifier %s", parameters), httpException);
            }
        } catch (BadJWSException e) {
            log.error("Bad signature in service record response", e);
            throw new ServiceRegistryLookupException("Bad signature in service record response", e);
        }
        return recordWrapper;
    }

    private List<ServiceRecord> loadServiceRecords(Parameters parameters) {
        ServiceRecord[] serviceRecords = {};
        try {
            final String resource = client.getResource("identifier/" + parameters.getIdentifier(), parameters.getQuery());
            final DocumentContext documentContext = JsonPath.parse(resource, jsonPathConfiguration());
            serviceRecords = documentContext.read("$.serviceRecords", ServiceRecord[].class);
        } catch(HttpClientErrorException httpException) {
            if (Arrays.asList(HttpStatus.NOT_FOUND, HttpStatus.UNAUTHORIZED).contains(httpException.getStatusCode())) {
                log.warn("RestClient returned {} when looking up service record with identifier {}",
                        httpException.getStatusCode(), parameters, httpException);
            } else {
                throw new ServiceRegistryLookupException(String.format("RestClient threw exception when looking up service record with identifier %s", parameters), httpException);
            }
        } catch (BadJWSException e) {
            log.error("Bad signature in service record response", e);
            throw new ServiceRegistryLookupException("Bad signature in service record response", e);
        }
        return Lists.newArrayList(serviceRecords);
    }

    /**
     * Method to fetch the info record for the given identifier
     * @param identifier of the receiver
     * @return an {@link InfoRecord} for the respective identifier
     */
    public InfoRecord getInfoRecord(String identifier) {
        Notification notification = properties.isVarslingsplikt()? Notification.OBLIGATED : Notification.NOT_OBLIGATED;
        return irCache.getUnchecked(new Parameters(identifier, notification));
    }

    private InfoRecord loadInfoRecord(Parameters parameters) {
        final String infoRecordString;
        try {
            infoRecordString = client.getResource("identifier/" + parameters.getIdentifier(), parameters.getQuery());
        } catch (BadJWSException e) {
            throw new ServiceRegistryLookupException("Bad signature in response from service registry", e);
        }
        final DocumentContext documentContext = JsonPath.parse(infoRecordString, jsonPathConfiguration());
        return documentContext.read("$.infoRecord", InfoRecord.class);
    }

    public String getSasKey() {
        // Single entry, key does not matter
        try {
            return skCache.get("");
        } catch (ExecutionException e) {
            throw new ServiceRegistryLookupException("An error occured when fetching SAS key", e);
        }
    }

    public void invalidateSasKey() {
        skCache.invalidateAll();
    }

    private String loadSasKey() throws SasKeyException {
        try {
            String sasKey = client.getResource("sastoken");
            // persist SAS key in case of ServiceRegistry down time
            sasKeyRepository.deleteAll();
            sasKeyRepository.save(SasKeyWrapper.of(sasKey));
            return sasKey;
        } catch (BadJWSException | RestClientException e) {
            log.error("An error occured when fetching SAS key from ServiceRegistry. Checking for persisted key..", e);
        }

        List<SasKeyWrapper> key = sasKeyRepository.findAll();
        if (key.isEmpty()) {
            throw new SasKeyException("No persisted SAS key found. Need to wait until connection with ServiceRegistry has been re-established..");
        }
        if (key.size() > 1) {
            log.error("SAS key repository should only ever have one entry - deleting all entries..");
            sasKeyRepository.deleteAll();
            throw new SasKeyException("Multiple keys found in repository, waiting for retry");
        }

        log.info("Loading locally persisted SAS key");
        return key.get(0).saskey;
    }

    private Configuration jsonPathConfiguration() {
        final Gson gson = new GsonBuilder().serializeNulls().create();
        return Configuration.defaultConfiguration().jsonProvider(new GsonJsonProvider(gson)).mappingProvider(new GsonMappingProvider());
    }
}
