package no.difi.meldingsutveksling.noarkexchange.putmessage;

import no.difi.meldingsutveksling.config.IntegrasjonspunktProperties;
import no.difi.meldingsutveksling.core.EDUCore;
import no.difi.meldingsutveksling.core.EDUCoreConverter;
import no.difi.meldingsutveksling.logging.Audit;
import no.difi.meldingsutveksling.noarkexchange.MessageException;
import no.difi.meldingsutveksling.noarkexchange.MessageSender;
import no.difi.meldingsutveksling.noarkexchange.StatusMessage;
import no.difi.meldingsutveksling.noarkexchange.schema.AppReceiptType;
import no.difi.meldingsutveksling.noarkexchange.schema.PutMessageResponseType;

import static java.util.Arrays.asList;
import static no.difi.meldingsutveksling.core.EDUCoreMarker.markerFrom;
import static no.difi.meldingsutveksling.noarkexchange.PutMessageResponseFactory.createOkResponse;

/**
 * Strategy for handling appreceipt messages.
 *
 * @author Glenn Bech
 */
class AppReceiptMessageStrategy implements MessageStrategy {

    private final MessageSender messageSender;
    private final IntegrasjonspunktProperties properties;

    public AppReceiptMessageStrategy(MessageSender messageSender, IntegrasjonspunktProperties properties) {
        this.messageSender = messageSender;
        this.properties = properties;
    }

    @Override
    public PutMessageResponseType send(EDUCore request) {
        Audit.info("Received AppReceipt", markerFrom(request));
        AppReceiptType receipt = EDUCoreConverter.payloadAsAppReceipt(request.getPayload());
        if (asList("OK", "WARNING", "ERROR").contains(receipt.getType())) {
            if ("p360".equalsIgnoreCase(properties.getNoarkSystem().getType())) {
                request.swapSenderAndReceiver();
            }
            messageSender.sendMessage(request);
        }
        if ("OK".equals(receipt.getType())) {
            Audit.info("AppReceipt sent to "+ request.getReceiver().getIdentifier(), markerFrom(request));
        } else if (asList("ERROR", "WARNING").contains(receipt.getType())) {
            final MessageException me = new MessageException(StatusMessage.APP_RECEIPT_CONTAINS_ERROR);
            Audit.warn(me.getStatusMessage().getTechnicalMessage(), markerFrom(request));
        }
        return createOkResponse();
    }


}
