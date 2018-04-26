package no.difi.meldingsutveksling.ptv;

import no.altinn.schemas.serviceengine.formsengine._2009._10.TransportType;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.AttachmentsV2;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.ExternalContentV2;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.InsertCorrespondenceV2;
import no.altinn.schemas.services.serviceengine.correspondence._2010._10.ObjectFactory;
import no.altinn.schemas.services.serviceengine.notification._2009._10.*;
import no.altinn.schemas.services.serviceengine.subscription._2009._10.AttachmentFunctionType;
import no.altinn.services.serviceengine.correspondence._2017._02.InsertCorrespondenceAECV2;
import no.altinn.services.serviceengine.reporteeelementlist._2010._10.BinaryAttachmentExternalBEV2List;
import no.altinn.services.serviceengine.reporteeelementlist._2010._10.BinaryAttachmentV2;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public class CorrespondenceAgencyClientTest {
    static Logger log = LoggerFactory.getLogger(CorrespondenceAgencyClientTest.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            usage(args);
            System.exit(1);
        }
        InsertCorrespondenceAECV2 insertCorrespondenceV2 = createInsertCorrespondenceV2();
        final CorrespondenceAgencyClient correspondenceAgencyClient = new CorrespondenceAgencyClient(null, null);
        final CorrespondenceRequest request = new CorrespondenceRequest.Builder().withUsername(args[0]).withPassword(args[1]).withPayload(insertCorrespondenceV2).build();
        correspondenceAgencyClient.sendCorrespondence(request);
    }

    private static void usage(String[] args) {
        System.out.format("Usage: %s %s [username] [password]", "java", CorrespondenceAgencyClientTest.class.getName());
    }

    private static InsertCorrespondenceAECV2 createInsertCorrespondenceV2() {
        ObjectFactory objectFactory = new ObjectFactory();

        String systemUserCode = "AAS_TEST";
        String externalReference = "12345678";
        InsertCorrespondenceV2 correspondence = new InsertCorrespondenceV2();

        correspondence.setServiceCode(objectFactory.createInsertCorrespondenceV2ServiceCode("4255"));
        correspondence.setServiceEdition(objectFactory.createInsertCorrespondenceV2ServiceEdition("4"));
        correspondence.setReportee(objectFactory.createInsertCorrespondenceV2Reportee("910926551"));

        ExternalContentV2 externalContentV2 = new ExternalContentV2();
        externalContentV2.setLanguageCode(objectFactory.createExternalContentV2LanguageCode("1044"));
        externalContentV2.setMessageTitle(objectFactory.createExternalContentV2MessageTitle("Dette er en test"));
        externalContentV2.setMessageSummary(objectFactory.createExternalContentV2MessageSummary("Her kommmer meldingssammendraget."));
        externalContentV2.setMessageBody(objectFactory.createExternalContentV2MessageBody("&lt;html>Dette er en test&lt;/html>"));

        AttachmentsV2 attachmentsV2 = new AttachmentsV2();
        BinaryAttachmentExternalBEV2List attachmentExternalBEV2List = new BinaryAttachmentExternalBEV2List();

        BinaryAttachmentV2 binaryAttachmentV2 = new BinaryAttachmentV2();
        binaryAttachmentV2.setFunctionType(AttachmentFunctionType.fromValue("Unspecified"));
        no.altinn.services.serviceengine.reporteeelementlist._2010._10.ObjectFactory reporteeFactory = new no.altinn.services.serviceengine.reporteeelementlist._2010._10.ObjectFactory();
        binaryAttachmentV2.setFileName(reporteeFactory.createBinaryAttachmentV2FileName("brev_med_innhold.pdf"));
        binaryAttachmentV2.setName(reporteeFactory.createBinaryAttachmentV2Name("Brev"));
        binaryAttachmentV2.setEncrypted(false);
        binaryAttachmentV2.setSendersReference(reporteeFactory.createBinaryAttachmentV2SendersReference("AttachmentReference_as123452"));


        try {
            byte[] data = FileUtils.readFileToByteArray(new File("src/test/resources/test_data"));
            binaryAttachmentV2.setData(reporteeFactory.createBinaryAttachmentV2Data(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        attachmentExternalBEV2List.getBinaryAttachmentV2().add(binaryAttachmentV2);
        attachmentsV2.setBinaryAttachments(objectFactory.createAttachmentsV2BinaryAttachments(attachmentExternalBEV2List));
        externalContentV2.setAttachments(objectFactory.createExternalContentV2Attachments(attachmentsV2));
        correspondence.setContent(objectFactory.createInsertCorrespondenceV2Content(externalContentV2));

        XMLGregorianCalendar date = fiveMinutesFromNow();
        correspondence.setVisibleDateTime(date);
        correspondence.setAllowSystemDeleteDateTime(objectFactory.createInsertCorrespondenceV2AllowSystemDeleteDateTime(date));

        NotificationBEList notifications = new NotificationBEList();
        Notification2009 notification = new Notification2009();
        no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory notificationFactory = new no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory();
        notification.setFromAddress(notificationFactory.createNotification2009FromAddress("no-reply@altinn.no"));
        notification.setShipmentDateTime(date);
        notification.setLanguageCode(notificationFactory.createNotification2009LanguageCode("1044"));
        notification.setNotificationType(notificationFactory.createNotification2009NotificationType("offentlig_etat"));

        TextTokenSubstitutionBEList tokens = new TextTokenSubstitutionBEList();
        tokens.getTextToken().add(createTextToken(0, "Avsender"));
        tokens.getTextToken().add(createTextToken(1, "Idrett og kultur"));
        tokens.getTextToken().add(createTextToken(2, "$reporteeName$"));

        notification.setTextTokens(notificationFactory.createNotification2009TextTokens(tokens));

        JAXBElement<ReceiverEndPointBEList> receiverEndpoints = createReceiverEndPoint();

        notification.setReceiverEndPoints(receiverEndpoints);

        correspondence.setAllowForwarding(objectFactory.createInsertCorrespondenceV2AllowForwarding(false));
        correspondence.setMessageSender(objectFactory.createInsertCorrespondenceV2MessageSender("Avsender"));

        notifications.getNotification().add(notification);
        correspondence.setNotifications(objectFactory.createInsertCorrespondenceV2Notifications(notifications));

        no.altinn.services.serviceengine.correspondence._2017._02.ObjectFactory of = new no.altinn.services.serviceengine.correspondence._2017._02.ObjectFactory();
        InsertCorrespondenceAECV2 insertCorrespondenceAECV2 = of.createInsertCorrespondenceAECV2();
        insertCorrespondenceAECV2.setCorrespondence(correspondence);
        insertCorrespondenceAECV2.setExternalShipmentReference("12345678");

        return insertCorrespondenceAECV2;
    }

    private static TextToken createTextToken(int num, String value) {
        no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory objectFactory = new no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory();
        TextToken textToken = new TextToken();
        textToken.setTokenNum(num);
        textToken.setTokenValue(objectFactory.createTextTokenTokenValue(value));

        return textToken;
    }

    private static JAXBElement<ReceiverEndPointBEList> createReceiverEndPoint() {
        no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory objectFactory = new no.altinn.schemas.services.serviceengine.notification._2009._10.ObjectFactory();
        ReceiverEndPoint receiverEndPoint = new ReceiverEndPoint();
        receiverEndPoint.setTransportType(objectFactory.createReceiverEndPointTransportType(TransportType.fromValue("Email")));
        ReceiverEndPointBEList receiverEndpoints = new ReceiverEndPointBEList();
        receiverEndpoints.getReceiverEndPoint().add(receiverEndPoint);
        return objectFactory.createNotification2009ReceiverEndPoints(receiverEndpoints);
    }

    private static XMLGregorianCalendar fiveMinutesFromNow() {
        return toXmlGregorianCalendar(ZonedDateTime.now().plusMinutes(5));
    }

    private static XMLGregorianCalendar toXmlGregorianCalendar(ZonedDateTime date) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar.from(date));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Could not convert DateTime to " + XMLGregorianCalendar.class, e);
        }
    }
}