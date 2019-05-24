package no.difi.meldingsutveksling.noarkexchange;

import lombok.experimental.UtilityClass;
import no.difi.meldingsutveksling.nextmove.ArkivmeldingKvitteringMessage;
import no.difi.meldingsutveksling.noarkexchange.schema.AppReceiptType;
import no.difi.meldingsutveksling.noarkexchange.schema.StatusMessageType;

@UtilityClass
public class AppReceiptFactory {

    public AppReceiptType from(ArkivmeldingKvitteringMessage receipt) {
        AppReceiptType appReceipt = new AppReceiptType();
        appReceipt.setType(receipt.getReceiptType());
        receipt.getMessages().forEach(sm -> {
            StatusMessageType statusMessageType = new StatusMessageType();
            statusMessageType.setText(sm.getText());
            statusMessageType.setCode(sm.getCode());
            appReceipt.getMessage().add(statusMessageType);
        });
        return appReceipt;
    }
}