---
title: Oppsett av eInnsyn 
pageid: Oppsett_av_eInnsyn 
layout: default
description: Oppsett av eInnsyn 
isHome: false
---

1. Start med å opprette en mappe med navn integrasjonspunkt på for eksempel c:\
2. Last så ned [denne filen](../resources/integrasjonspunkt-local.properties) og lagre i overnevnte mappe
3. Fyll inn verdier i integrasjonspunkt-local.properties ( se tabell under ) 

> **MERK** Du må velge om du vil bruke java keystore eller Windows certificate store. Du må dermed kommentere bort linjer du ikke trenger i integrasjonspunkt-local.properties filen du laster ned. Dette står videre forklart som kommentarer i selve filen.

Bruk av Windows certificate store krever minimum versjon 1.7.74... av integrasjonspunktet.

### integrasjonspunkt-local.properties

Følgende verdier settes i integrasjonspunkt-local.properties

**NB:** Benytt skråstrek (/) eller dobbel omvendt skråstrek (\\\\) som ressursdeler når dere angir filbaner.

| Properties | Eksempel Java Keystore(JKS) |Windows certificate store(WCS)| Beskrivelse | 
| --- | --- | --- | --- |
| server.port | 9093 | 9093 | Portnummer integrasjonspunktet skal kjøre på (default 9093)  | 
| difi.move.org.number | 123456789 | 123456789 |Organisasjonsnummer til din organisasjon (9 siffer) | 
| difi.move.org.keystore.alias  | alias | Egendefinert navn | alias=navnet på virksomhetssertifikatet som ligger i JKS(case sensitivt) | 
| difi.move.org.keystore.password | changeit | *tom* | Passord til java keystore. WCS = sett som blank | 
| difi.move.org.keystore.path | difi.move.org.keystore.path=file:c:/integrasjonspunkt/keystore.jks | *tom* |Path til .jks fil. WCS= sett som blank | 
| difi.move.org.keystore.type | *tom* | WindowsMY| Forteller Java at en skal bruke WCS | 
| | | | |

Når du er ferdig skal strukturen på området se slik ut:
```
c:/
|-- integrasjonspunkt/
   |-- integrasjonspunkt-local.properties
   |-- integrasjonspunkt[versjon].jar
```

**Integrasjonspunkt[versjon].jar** filen finner du øverst i denne installasjonsveiledningen.

### Start Integrasjonspunktet

Integrasjonspunktet startes fra kommandolinjen med følgende kommandoer for henholdsvis test og produksjon. For å starte integrasjonspunktet kreves visse minimum brukerrettigheter, [les mer om dette her](http://difi.github.io/move-integrasjonspunkt/vStaging/#/5_brukerrettigheter). Eller så kan en eventuelt starte kommandovinduet som administrator og dermed også ha rettigheter til å starte det.

> TEST
```powershell
java -jar -Dspring.profiles.active=staging integrasjonspunkt-[versjon].jar --app.logger.enableSSL=false 
```

> PROD
```powershell
java -jar integrasjonspunkt-[versjon].jar --app.logger.enableSSL=false 
```

Sjekk i nettleser når Integrasjonspunktet har startet, som gir response i form av en wsdl.

```
http://localhost:<port-til-integrasjonspunkt>/noarkExchange?wsdl
```


> Har du fulgt installasjonsguiden helt hit så skal du være ferdig med installasjon av integrasjonspunktet for einnsyn, gratulerer! Om du har spørsmål eller behøver hjelp, kontakt oss gjerne på <idporten@difi.no> 

*Den neste hovedoverskriften gjelder ikke for eInnsyn, men for DPI, DPO, DPV*

***
