---
title: Selvhjelp og nyttig informasjon
description: Selvhjelp og nyttig informasjon som øking av loggnivå, loggrullering, trafikkflyt mm. 
summary: "Selvhjelp og nyttig informasjon om eFormidling"
permalink: selfhelp.html
sidebar: veiledning_sidebar
folder: veiledning
---

### Eformidling på 2 minutter - dette må du vite

- DPV = Digital post til virksomheter. Eget brukernamn/passord. Brukernamn/passord opprettes av Altinn og passord mottas på SMS. [Mer](https://difi.github.io/move-integrasjonspunkt/create_users.html#opprette-dpv-bruker)
- DPF = . Brukernamn/passord for både svarut og svarinn (ulike). Begge settene med brukernamn/passord genereres/hentes på [https://svarut.ks.no/](https://svarut.ks.no/). [Veiledning finner du her](https://difi.github.io/move-integrasjonspunkt/create_users.html#opprette-dpf-brukere-svarinn-og-svarut)
- DPO = Digital post til offentlige virksomheter. Eget brukernamn/passord. [Opprettes selv](https://difi.github.io/move-integrasjonspunkt/create_users.html#opprette-dpo-bruker-altinn-formidlingstjeneste) 
- Virksomheten må ha et [virksomhetssertifikat](https://difi.github.io/move-integrasjonspunkt/virksomhetssertifikat.html). Offentlig nøkkel sendes til Difi. 
- Integrasjonspunktet både sender og mottar meldinger.
- "Started IntegrasjonspunktApplication ..." blir logget i ```\integrasjonspunkt\integrasjonspunkt-logs\application.log``` like etter fullført oppstart.
- Bruksvilkår/avtale må være på plass for å bruke eFormidling. Se lenke under.
- [Mer om eFormidling og hvordan det fungerer](https://difi.github.io/eformidling/index.html)

For informasjon om ikke tekniske ting og bruksvilkår/avtaler [se Samarbeidsportalen](https://samarbeid.difi.no/felleslosninger/eformidling/ta-i-bruk-eformidling/1-forberedelser)

### Loggrullering ved installasjon som Windows service

Om integrasjonspunktet ditt er satt opp til å kjøre som en Windows-tjeneste så kan en enkelt også aktivere loggrullering.

Du kan rotere logger på størrelse og samtidig velge hvor mange en ønsker å ta vare på. standardstørrelsen her er 10MB, denne kan du endre til ønsket størrelse. Antall filer som blir tatt vare på er 8. Dette kan også endres. Sørg for at dette er innenfor ``` <service> </service> ``` taggen slik som resten av konfigurasjonen.

```
<log mode="roll-by-size">
	<sizeThreshold>10240</sizeThreshold>
	<keepFiles>8</keepFiles>
</log> 
```

Ferdig konfigurert ser det feks slik ut:

```
<service>
            <id>einnsyn-integrasjonspunkt</id>
            <name>einnsyn-integrasjonspunkt</name>
            <description>Klient for opplasting av journaldata og nedlasting av innsynskrav</description>
            <argument>-jar</argument>
            <argument>integrasjonspunkt-1.7.93.jar</argument>
            <argument>--app.logger.enableSSL=false</argument>
            <logpath>%BASE%/integrasjonspunkt-logs</logpath>
            <log mode="roll-by-size">
                <sizeThreshold>10240</sizeThreshold>
                <keepFiles>8</keepFiles>
            </log>
            <executable>java</executable>
</service>
```

### Justere tilgjengelig minne

For å justere hvor mye minne integrasjonspunktet kan bruke så kan dette gjøres ved å endre oppstartkommandoen. Dette kan være veldig nyttig ved forsendelser via eFormidling (dpo,dpv,dpi,dpf) for å være sikker på at applikasjonen har nok minne til å sende større filer. 1GB minne burde holde for eInnsyn, men for eFormidling bør en gjerne sette minst 2GB, gjerne mer. 

```-Xmx2048m``` setter minimum antall minne som applikasjonen har tilgjengelig

#### fra kommandolinje 
Du må legge inn ``` -Xmx2048m``` i oppstartskommandoen for å sette feks 2048 MB. Antallet kan justeres til ønske. 
Feks: ```java -jar integrasjonspunkt-[versjon].jar --app.logger.enableSSL=false -Xmx2048m```

#### I Windows service
For å gjøre dette på en service så må en legge inn enda et argument i integrasjonspunkt-service.xml-filen. feks slik: ```<argument>-Xmx2048m</argument>```. Sørg for at det er innen for ```<service>...</service>``` om du har satt opp integrasjonspunktet som en Windows tjeneste.

Feks:

```
<service>
            <id>einnsyn-integrasjonspunkt</id>
            <name>einnsyn-integrasjonspunkt</name>
            <description>Klient for opplasting av journaldata og nedlasting av innsynskrav</description>
            <argument>-jar</argument>
            <argument>integrasjonspunkt-1.7.93.jar</argument>
            <argument>--app.logger.enableSSL=false</argument>
            <argument>-Xmx2048m</argument>
            <logpath>%BASE%/integrasjonspunkt-logs</logpath>
            <log mode="roll-by-size">
                <sizeThreshold>10240</sizeThreshold>
                <keepFiles>8</keepFiles>
            </log>
            <executable>java</executable>
</service>
```



### Øke loggnivået ved behov

Det er mulig å øke loggnivået på integrasjonspunktet. Dette gjøres hovedsaklig kun under feilsøking og vil føre til mye ekstra loggmeldinger. Legg inn følgende i "integrasjonspunkt-local.properties" filen.

```
logging.level.org.springframework.ws.client.MessageTracing=TRACE
logging.level.org.springframework.ws.server.MessageTracing=TRACE
```

### Batch read for eInnsyn-meldinger

Fra og med versjon 1.7.82 av integrasjonspunktet er det mulig å bruke batch read når en leser meldinger fra Azure Service-bus. Dette gjøres ved å benytte Advanced Message Queuing Protocol (AMQP). [Les mer her](https://docs.microsoft.com/en-us/azure/service-bus-messaging/service-bus-performance-improvements) Dette fungerer ikke via rest-grensesnittet. 

For å aktivere Batch read i ditt integrasjonspunkt så må du ha versjon 1.7.82 eller nyere og legge inn følgende i *integrasjonspunkt-local.properties* filen ```difi.move.nextmove.serviceBus.batchRead=true```. I tillegg må du åpne port 5671 for utgående trafikk. 

---
