---
title: Kjøre integrasjonspunktet
description: Hvordan man kjører integrasjonspunktet.
summary: "Hvordan man kjører integrasjonspunktet."
sidebar: veiledning_sidebar
permalink: ip_run.html
folder: veiledning
---

Det finnes flere måter å kjøre integrasjonspunktet på. Den vi anbefaler for enkel start/stopp i tillegg til tilgangsstyring er å installere integrasjonspunktet som en tjeneste. Her vil vi vise tre forskjellige måter å kjøre integrasjonspunktet på.

### Justere minnebruk (nyttig for eformidling)

For å justere hvor mye minne integrasjonspunktet kan bruke så kan dette gjøres ved å endre oppstartkommandoen. Dette kan være veldig nyttig ved forsendelser via eFormidling (dpo,dpv,dpi,dpf) for å være sikker på at applikasjonen har nok minne til å sende større filer. 1GB minne burde holde lenge, men det kan variere.

Du må legge inn ``` -Xmx1024m``` i oppstartskommandoen for å sette feks 1024 MB. Antallet kan justeres til ønsket mengde. For å gjøre dette på en service så må en legge inn enda et argument i integrasjonspunkt-service.xml-filen. feks slik: ```<argument>-Xmx2048m</argument>```. Sørg for at det er innen for ```<service>...</service>``` om du har satt opp integrasjonspunktet som en Windows tjeneste.


## Alt 1: Kjøre integrasjonspunktet som en tjeneste

Integrasjonspunktet kan også installeres som en tjeneste på server. For å gjøre dette kan en laste ned en tredjepartsprogramvare og sette opp en egen liten config-fil.

Dokumentasjonen på programvaren du trenger ligger [på github](https://github.com/kohsuke/winsw). Du trenger to filer: .exe -filen fra dette programmet og en egen .xml-fil for å fortelle .exe -filen hvilke innstillinger som skal brukes. Dette er samme konseptet som [einnsyn-klient installasjonen er basert på](https://difi.github.io/einnsyn-klient/). 

1. Last ned Winsw.exe [her](https://github.com/kohsuke/winsw/releases). Mer informasjon om hvilken versjon du skal velge står [her: Supported .NET versions](https://github.com/kohsuke/winsw#user-content-supported-net-versions). Om du er usikker på hvilken .NET versjon du har, [les her](https://support.microsoft.com/nb-no/help/318785/how-to-determine-which-versions-and-service-pack-levels-of-the-microso)
2. last ned konfigurasjonsfila vår for [testmiljø](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/staging/integrasjonspunkt-service.xml) eller [produksjonsmiljø](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/integrasjonspunkt-service.xml)
3. Endre navn på .exe fila og xml-filene til de navnene du ønsker. For eksempel integrasjonspunkt-service.exe og integrasjonspunkt-service.xml. (begge må ha samme navn)
4. Legg begge disse filene i integrasjonspunktmappa di.
5. Endre versjonsnummeret på integrasjonspunkt-%versjonsnr%.jar til å være lik din versjon
* For å installere tjenesten gjør du følgende:
  - åpne kommandovindu som administrator og naviger til integrasjonspunktmappa. Kjør så følgende kommando
  ```
  integrasjonspunkt-service.exe install
  integrasjonspunkt-service.exe start
  ```

I denne config-fila er det lagt inn automatisk loggrotering ved 10MB størrelse og 8 filer vil bli beholdt. Dette kan endres til ønsket størrelse ved å endre ```<sizeThreshold>```variabelen.  Om du ikke ønsker loggrotering kan du fjerne hele ```<logmode>``` fra integrasjonspunkt-service.xml

Loggene for denne tjenesten vil i utgangspunktet bli skrevet til feks ```c:\integrasjonspunkt\integrasjonspunkt-logs``` og filen integrasjonspunkt-service.out. 

Om du ønsker å kjøre integrasjonspunktet på en minste rettighetsbruker så kan du enkelt endre hvilken bruker som kjører tjenesten ved å høyreklikke på den, velge "properties" og så velge "logg på" fanen. [Hvordan opprette en minste rettighetsbruker.](http://difi.github.io/move-integrasjonspunkt/ip_run.html#alt-3-kj%C3%B8re-via-task-scheduler-med-minste-rettigheter)

#### Loggrotering
I tillegg kan du legge inn loggrotering om det er ønskelig. Dermed kan du rotere logger på størrelse og velge hvor mange en ønsker å ta vare på. standardstørrelsen her er 10MB, denne kan du endre til ønsket størrelse. Antall filer som blir tatt vare på er 8. Dette kan også endres. Sørg for at dette er innenfor ``` <service> </service> ``` taggen slik som resten av konfigurasjonen.

```
<log mode="roll-by-size">
	<sizeThreshold>10240</sizeThreshold>
	<keepFiles>8</keepFiles>
</log> 
```

### Reinstallasjon av tjenesten

Om du gjør endringer i versjon / ip-service.xml fil så må du reinstallere tjenesten. Det gjør du ved å åpne kommandovindu som administrator og navigere til integrasjonspunktmappa. Kjør så følgende kommandoer.

```
integrasjonspunkt-service.exe stop
integrasjonspunkt-service.exe uninstall
integrasjonspunkt-service.exe install
integrasjonspunkt-service.exe start
```

Da er tjenesten reinstallert og restartet.


## Alt 2: Kjøre Integrasjonspunktet fra kommandovindu

Integrasjonspunktet startes fra kommandolinjen med følgende kommandoer for henholdsvis test og produksjon. For å starte integrasjonspunktet kreves visse minimum brukerrettigheter, [les mer om dette her](http://difi.github.io/move-integrasjonspunkt/ip_run.html#alt-3-kj%C3%B8re-via-task-scheduler-med-minste-rettigheter). Eller så kan en eventuelt starte kommandovinduet som administrator og dermed også ha rettigheter til å starte det.

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

Merk: Om du kjører integrasjonspunktet fra kommandolinjen så må dette vinduet stå åpent. Eventuelt så kan du endre ```java -jar``` i kommandoen til ```javaw -jar```. Da vil det kjøre uten kommandovinduet, men du vil måtte lukke det ved å finner prosessen i task manager / oppgavebehandling og stoppe den der. 

## Alt 3: Kjøre via task scheduler med minste rettigheter


Når en skal starte integrasjonspunktet så kreves det visse rettigheter på denne brukeren for at programmet skal kunne fungere. 

**Opprette Lokal bruker type user:**

%servernavn%\integrasjonspunkt
 
**Sette rettar for brukar i local security policy (deaktivere påloggingsmulighet):**

- Deny log on locally
- Deny log on thru remote desktop service 
- Deny access to this computer from the network 
- Log on as a batch job (for å kunne kjøre taskscheduler)
 
**Bruker må ha tilgang på mappen der integrasjonspunktfilene ligger**

Egenskaper på mappen
  * Security:
    * Legg til integrasjonspunkt brukeren med modify rettigheter
  
### Kjøre kommandoen i "Task Scheduler"

**general:**

user: %servernavn%\integrasjonspunkt
- Run whether user is logged on or not
 
**Trigger:**
* At startup
   * Edit action
   * Program/script: JAVA
   * add argument (optional):
        * -jar integrasjonspunkt-%versjonsnr%.jar --app.logger.enableSSL=false
   * Start in (optional):
        * "disk:\mappenavn» til integrasjonspunktet"


![Taskscheduler](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/taskscheduler.PNG)


Merk: om du skal starte integrasjonspunktet i staging-miljø må du bruke følgende argument i stedet: ```-jar -Dspring.profiles.active=staging integrasjonspunkt-%versjonsnr%.jar --app.logger.enableSSL=false```




