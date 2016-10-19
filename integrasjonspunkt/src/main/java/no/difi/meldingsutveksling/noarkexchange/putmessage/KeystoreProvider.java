package no.difi.meldingsutveksling.noarkexchange.putmessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.ptp.MeldingsformidlerException;

public class KeystoreProvider {

    private static String location;
    private static String password;
    private final KeyStore keystore;

    public KeystoreProvider(KeyStore keyStore) {
        this.keystore = keyStore;
    }

    public static KeystoreProvider from(IntegrasjonspunktProperties properties) throws MeldingsformidlerException {
        location = properties.getDpi().getKeystore().getPath();
        password = properties.getDpi().getKeystore().getPassword();
        final KeyStore keyStore = loadKeyStore(location, password.toCharArray());
        return new KeystoreProvider(keyStore);
    }

    private static KeyStore loadKeyStore(String filename, char[] password) throws MeldingsformidlerException {
        KeyStore keystore = null;
        try {
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            throw new MeldingsformidlerException("Could not initialize keystore", e);
        }
        try (FileInputStream file = new FileInputStream(filename)) {
            keystore.load(file, password);
        } catch (IOException e) {
            throw new MeldingsformidlerException("Could not open keystore file", e);
        } catch (CertificateException | NoSuchAlgorithmException e) {
            throw new MeldingsformidlerException("Unable to load keystore file", e);
        }

        return keystore;
    }

    public KeyStore getKeyStore() {
        return keystore;
    }

}