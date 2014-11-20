package no.difi.meldingsutveksling.oxalisexchange;

import no.difi.meldingsutveksling.domain.ByteArrayFile;

/**
 * Created by kubkaray on 20.11.2014.
 */
public class ByteArrayImpl implements ByteArrayFile {

    byte[] bytes;
    String fileName;
    String mimeType;

    public ByteArrayImpl(byte[] bytes, String fileName, String mimeType) {
        this.bytes = bytes;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }
}
