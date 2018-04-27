package no.difi.meldingsutveksling.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.net.URL;

/**
 * Configurable properties for Integrasjonspunkt.
 *
 * @author kons-nlu
 */
@Data
@ConfigurationProperties(prefix = "difi.move")
public class IntegrasjonspunktProperties {

    @Valid
    private Organization org;

    /**
     * Service registry endpoint.
     */
    @NotNull(message = "Service registry must be configured")
    private String serviceregistryEndpoint;

    @Valid
    private AltinnFormidlingsTjenestenConfig dpo;

    @Valid
    private PostVirksomheter dpv;

    @Valid
    private NorskArkivstandardSystem noarkSystem;

    @Valid
    private MessageServiceHandler msh = new MessageServiceHandler();

    @Valid
    private DigitalPostInnbyggerConfig dpi;

    @Valid
    private FiksConfig fiks = new FiksConfig();

    @Valid
    private Oidc oidc;

    private Mail mail;

    @Valid
    private NextMove nextmove;

    @Valid
    private Sign sign;

    @Valid
    private Ntp ntp;

    @Valid
    private Queue queue;

    /**
     * Use this parameter to indicate that the message are related to vedtak/messages that require the recipient to be
     * notified. This parameter is passed over to ServiceRegistry to determine where the message should be sent.
     * (See http://begrep.difi.no/SikkerDigitalPost/1.2.3/forretningslag/varsling for more information)
     */
    private boolean varslingsplikt = false;

    @Data
    public static class Ntp {
        @NotNull
        private String host;
        private boolean disable;
    }

    @Data
    public static class Queue {
        @NotNull
        private Integer maximumRetryHours;
    }

    /**
     * Feature toggles.
     */
    @Valid
    private FeatureToggle feature;

    public FeatureToggle getFeature() {
        if (this.feature == null) {
            this.feature = new FeatureToggle();
        }
        return this.feature;
    }

    @Data
    public static class Organization {

        /**
         * Organization number to run as.
         */
        @NotNull(message = "difi.move.org.number is not set. This property is required.")
        @Size(min = 9, max = 9, message = "difi.move.org.number must be exactly 9 digits")
        private String number;

        /**
         * Business certificate for this instance.
         */
        @Valid
        @NotNull(message = "Certificate properties not set.")
        @NestedConfigurationProperty
        private KeyStoreProperties keystore;
    }

    @Data
    public static class PostVirksomheter {

        private URL endpointUrl;
        private String externalServiceCode;
        private String externalServiceEditionCode;
        private boolean notifyEmail;
        private boolean notifySms;
        private String notificationText;
        private KeyStoreProperties keystore;

    }

    /**
     * Idporten Oidc
     */
    @Data
    public static class Oidc {

        @NotNull
        private boolean enable;
        private URL url;
        private String audience;
        private String clientId;
        @NestedConfigurationProperty
        private KeyStoreProperties keystore;
    }

    /**
     * SR signing
     */
    @Data
    public static class Sign {

        private boolean enable;
        private Resource certificate;
    }

    /**
     * Mail settings for
     */
    @Data
    @ToString(exclude = "password")
    public static class Mail {

        private String smtpHost;
        private String smtpPort;
        private String senderAddress;
        private String receiverAddress;
        private String enableAuth;
        private String username;
        private String password;
        private String trust;
    }

    @Data
    public static class NextMove {

        @NotNull
        private String filedir;
        @NotNull
        private String asicfile;
        @NotNull
        private Integer lockTimeoutMinutes;
        @NotNull
        private Boolean applyZipHeaderPatch = Boolean.FALSE;
        @Valid
        private ServiceBus serviceBus;

    }

    @Data
    @ToString(exclude = "sasToken")
    public static class ServiceBus {

        private boolean enable;
        @NotNull
        private String sasKeyName;
        @NotNull
        private String sasToken;
        @Pattern(regexp = "innsyn|data", flags = Pattern.Flag.CASE_INSENSITIVE)
        private String mode;
        @NotNull
        private String namespace;
        private String receiptQueue;
        private Integer readMaxMessages;
        private boolean batchRead;
    }

    @Data
    @ToString(exclude = "password")
    public static class NorskArkivstandardSystem {

        private String endpointURL;
        private String username;
        private String password;
        /**
         * If the authentication is of type NTLM (Windows) this is the domain the username belongs to
         */
        private String domain;
        /**
         * The type of archive system you are using, eg. Ephorte, p360, websak, mail...
         */
        private String type;

    }

    @Data
    public static class MessageServiceHandler {

        private String endpointURL;
    }

    @Data
    public static class FeatureToggle {

        /**
         * Activate new internal queue.
         */
        private boolean enableQueue;

        private boolean enableReceipts;
        private boolean forwardReceivedAppReceipts;
        private boolean returnOkOnEmptyPayload;

        /**
         * Service toggles
         */
        private boolean enableDPO;
        private boolean enableDPV;
        private boolean enableDPI;
        private boolean enableDPF;
        private boolean enableDPE;

    }

    @Data
    public static class Sms {
        @Size(max=160)
        private String varslingstekst;
    }

}
