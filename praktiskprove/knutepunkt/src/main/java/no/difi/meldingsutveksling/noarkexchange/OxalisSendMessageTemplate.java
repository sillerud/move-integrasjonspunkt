package no.difi.meldingsutveksling.noarkexchange;

import no.difi.meldingsutveksling.domain.SBD;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class OxalisSendMessageTemplate extends SendMessageTemplate {
	
    @Override
    void sendSBD(SBD sbd) throws IOException {
    	FileUtils.writeByteArrayToFile(new File(System.getProperty("user.home") + "\\Dropbox\\DifiCmnDocs\\demo\\sbdUt.xml"), sbd.content);
    }
}
