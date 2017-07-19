

### einnsyn

### Oppsett

Start med å opprette en mappe med navn integrasjonspunkt på c:\

Last deretter ned integrasjonspunktet fra artifactory (se link i top av dokument) og legg den i overnevnte mappe
Opprett filen integrasjonspunkt-local.properties på området

### integrasjonspunkt-local.properties

Følgende verdier settes i integrasjonspunkt-local.properties

NB: Benytt skråstrek (/) eller dobbel omvendt skråstrek (\\\\) som resursdeler når dere angir filbaner.

#### For alle installasjoner

**Propertie**                          |**Beskrivelse**                                                                                               |**Eksempel**
---------------------------------------|--------------------------------------------------------------------------------------------------------------|-----------------
server.port                            |Portnummer integrasjonspunktet skal kjøre på (default 9093)                                                   |9093
                                       |                                                                                                              |
difi.move.org.number                   |Organisasjonsnummer til din organisasjon (9 siffer)                                                           |123456789
difi.move.org.keystore.path            |Path til .jks fil                                                                                             |c:/integrasjonspunkt/keystore.jks
difi.move.org.keystore.password        |Passord til keystore                                                                                          |changeit
difi.move.org.keystore.alias           |Alias til virksomhetssertifikatet som brukes i integrasjonspunktet                                            |alias
difi.move.nextbest.serviceBus.enable   |Skru på bruk av eInnsynsmeldinger                                                                             |true
difi.move.nextbest.serviceBus.sasToken |Token som må brukes for tilang til meldingsformidler                                                          |*Se infobrev om einnsyn*
