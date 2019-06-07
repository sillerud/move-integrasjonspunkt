package no.difi.meldingsutveksling.nextmove.v2;

/*
@RequiredArgsConstructor
@RunWith(MockitoJUnitRunner.class)
public class TimeToLiveHelperTest {
    StandardBusinessDocument sbd;

    private final ConversationService conversationService;
    private final ServiceIdentifier serviceIdentifier;
    private final ConversationDirection direction;

    @Mock
    TimeToLiveHelper timeToLiveHelper;


    @Before
    public void setUp() {

        sbd = getStandardBusinessDocument();
        timeToLiveHelper.registerErrorStatusAndMessage(sbd, serviceIdentifier, direction);
    }

    @Test
    public void registerErrorStatusAndMessage_shouldRegisterCorrectReceiptStatus() {
        //TODO implementere metode som sjekker om registrert status er registrert korrekt.

// asserte at rett status er registrert. Korleis ?
        // Ikkje mocke metoden, men oppførselen ? Should mock the beahaviour of the dependencies.
    }

    private StandardBusinessDocument getStandardBusinessDocument() {
        return new StandardBusinessDocument()
                .setStandardBusinessDocumentHeader(new StandardBusinessDocumentHeader()
                        .setBusinessScope(new BusinessScope()
                                .addScope(new Scope()
                                        .addScopeInformation(new CorrelationInformation()
                                                .setExpectedResponseDateTime(ZonedDateTime.parse("2025-02-10T00:31:52Z"))
                                        )
                                        .setIdentifier("urn:no:difi:arkivmelding:xsd::arkivmelding")
                                        .setInstanceIdentifier("37efbd4c-413d-4e2c-bbc5-257ef4a65a45")
                                        .setType("ConversationId")
                                )
                        )
                        .setDocumentIdentification(new DocumentIdentification()
                                .setCreationDateAndTime(ZonedDateTime.parse("2025-01-11T15:29:58.753+02:00"))
                                .setInstanceIdentifier("ff88849c-e281-4809-8555-7cd54952b916")
                                .setStandard("urn:no:difi:profile:arkivmelding:administrasjon:ver1.0")
                                .setType("ARKIVMELDING")
                                .setTypeVersion("2.0")
                        )
                        .setHeaderVersion("1.0")
                        .addReceiver(new Receiver()
                                .setIdentifier(new PartnerIdentification()
                                        .setAuthority("iso6523-actorid-upis")
                                        .setValue("9908:910075918")
                                )
                        )
                        .addSender(new Sender()
                                .setIdentifier(new PartnerIdentification()
                                        .setAuthority("iso6523-actorid-upis")
                                        .setValue("9908:910077473")
                                )
                        )
                );
    }
}*/
