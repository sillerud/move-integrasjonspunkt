package no.difi.meldingsutveksling.ptv;

import lombok.Builder;
import lombok.Data;

import java.security.KeyStore;

@Data
@Builder
public class CorrespondenceAgencyConfiguration {

    private String externalServiceEditionCode;
    private String externalServiceCode;
    private boolean notifyEmail;
    private boolean notifySms;
    private String notificationText;
    private String sender;
    private String nextmoveFiledir;
    private String endpointUrl;
    private KeyStore keyStore;

}
