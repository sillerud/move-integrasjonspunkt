package no.difi.meldingsutveksling.ptv;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.KeyStore;

@Data
@AllArgsConstructor(staticName = "of")
public class CorrespondenceRequest {
    private Object payload;
    private KeyStore keystore;

}
