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

### integrasjonspunkt-local.properties

Følgende verdier settes i integrasjonspunkt-local.properties

**NB:** Benytt skråstrek (/) eller dobbel omvendt skråstrek (\\\\) som resursdeler når dere angir filbaner.

| Properties | Eksempel | Beskrivelse | 
| --- | --- | --- |
| server.port | 9093 | Portnummer integrasjonspunktet skal kjøre på (default 9093)  | 
| difi.move.org.number | 123456789 | Organisasjonsnummer til din organisasjon (9 siffer) | 
| difi.move.org.keystore.alias  | alias | Alias til virksomhetssertifikatet som brukes i integrasjonspunktet(case sensitivt) | 
| difi.move.org.keystore.password | changeit | Passord til keystore | 
| difi.move.org.keystore.path | difi.move.org.keystore.path=file:c:/integrasjonspunkt/keystore.jks | Path til .jks fil | 
| difi.move.nextbest.serviceBus.enable | true | Skru på bruk av eInnsynsmeldinger |
| difi.move.nextbest.serviceBus.sasToken | *Se infobrev om einnsyn* | Token som må brukes for tilang til meldingsformidler | 
| | | | 

Når du er ferdig skal strukturen på området se slik ut:
```
c:/
|-- integrasjonspunkt/
   |-- integrasjonspunkt-local.properties
```

### Start Integrasjonspunktet

Integrasjonspunktet startes fra kommandolinjen med kommandoen (Kjør som administrator)

```powershell
java -jar -Dspring.profiles.active=staging integrasjonspunkt-[versjon].jar --app.logger.enableSSL=false 
```

Sjekk i nettleser når Integrasjonspunktet har startet, som gir response i form av en wsdl.

```
http://localhost:<port-til-integrasjonspunkt>/noarkExchange?wsdl
```


> Har du fulgt installasjonsguiden helt hit så skal du være ferdig med installasjon av integrasjonspunktet for einnsyn, gratulerer! Om du har spørsmål eller behøver hjelp, kontakt oss gjerne på <idporten@difi.no> 

*Den neste hovedoverskriften gjelder ikke for eInnsyn, men for DPI, DPO, DPV*

***
