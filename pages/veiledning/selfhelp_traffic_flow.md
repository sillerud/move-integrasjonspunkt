---
title: Trafikkflyt, kvitteringer og statuser i eFormidling og eInnsyn
description: Trafikkflyt, kvitteringer og statuser
summary: "Trafikkflyt, kvitteringer og statuser i eFormidling og eInnsyn"
permalink: selfhelp_traffic_flow.html
sidebar: veiledning_sidebar
folder: veiledning
---



### Digital post til offentlige virksomheter (DPO)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpo.jpg)

Utgående/innkommende: Vil bli initiert i sak-arkivsystemet og sendt til integrasjonspunktet. Denne meldingen blir lastet opp til Altinn's meldingsformidler og sendt videre til mottaker sitt integrasjonspunkt og mottas i sak-arkivsystemet. Appreceipt blir levert til avsender når mottakende integrasjonspunkt laster ned meldingen.

Integrasjonspunktet velger DPO som avsendermetode om både avsender og mottaker har fått tilganger til å bruke DPO ( Åpnet av Difi). Om mottaker ikke har konfigurert sitt integrasjonspunkt for mottak av DPO vil denne meldingen ikke komme frem før dette er gjort. Derfor er det viktig at virksomheter som skal bruke eFormidling sørger for å konfigurere integrasjonspunktet sitt riktig før de ber Difi om å åpne tilgang til DPO. 

Dersom en DPO-melding havner i Dead letter queue (DLQ) hos mottaker, sender mottaker en error appreceipt tilbake. Viss avsender ikke får leveringskvittering som avslutter polling innen satt timeout(24t), får meldingen feilstatus i statusgrensesnittet. 


### DPO statuser
 
 
  | Status | Logget av Avsender/mottaker | Kommentar |
  | :--- | :--- | :--- |  
  | OPPRETTET| Avsender | Integrasjonspunktet mottar BEST/EDU-melding fra sak-arkivsystem og oppretter en DPO-melding |
  | SENDT| Avsender| Integrasjonspunkt har sendt forsendelsen til meldingsformidler | 
  | OPPRETTET | Mottaker | Integrasjonspunkt laster frå meldingsformidler ned og oppretter meldingen hos seg | 
  | INNKOMMENDE_MOTTATT | Mottaker| Integrasjonspunkt hos mottaker har mottatt melding. | 
  | INNKOMMENDE_LEVERT | Mottaker | Har generert to mottakskvitteringer og disse blir sendt til avsender | 
  | LEVERING | Avsender | Avsender mottar bekreftelse på at mottakende integrasjonspunkt har mottatt. | 
  | AAPNING | Avsender | Videresending til sak-arkivsystem |  
  | LEST | Avsender| Appreceipter fra mottaker av avsender. Dette er både en kvittering og meldingstype som indikerer at sak-arkivsystemet hos mottaker har fått meldingen. Leveransen er bekreftet fullført. | 
  
---




### Digital post KS FIKS SvarInn/SvarUt (DPF)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpf.jpg)

Utgående: Melding blir initiert i sak-arkivsystemet og sendt til avsenders integrasjonspunkt. Meldinga blir sendt til KS FIKS sin meldingsformidler SvarInn, for så å bli ekspedert til mottaker sin SvarInn innboks. Appreceipt blir levert til avsender når SvarInn mottar forsendelsen. Tjenesten har leveringsgaranti, ref bruksvilkår hos KS.

Innkommende: Meldinger som blir sendt til SvarInn, enten via SvarUt eller som beskrevet over vil først blir lagt i SvarInn innboksen før integrasjonspunktet vil forsøke å laste ned meldinga for å sende den direkte til sak-arkivsystemet. 

Om mottaker ønsker det er det mulighet for å skru på e-postlevering av post frå SvarInn innboksen slik at en slipper å hente den der (om en ikke kan få det levert i sak-arkivsystemet. feks ved feil.). Dette tar eFormidling seg av vha e-postkonfigurasjonen som er satt opp i integrasjonspunkt-local.properties under DPF innstillingene. Da sender den via lokal smtp-server (den du konfigurerte). Dette må spesifikt settes på i properties-filen og er satt til false som default. ```difi.move.fiks.inn.mailOnError=false```.

eFormidling støtter også både sikkerhetsnivå 3 og 4. Ved forsendelser vil integrasjonspunktet slå opp mot Service Registry (adresse/tilgangsregister) for å sjekke det høyeste sikkerhetsnivået mottaker støtter. Meldinga blir dermed sendt på høyeste støttede sikkerhetsnivå.  Det er for e-postutsendelser dette er aktuelt, ved sikkerhetsnivå 3 ligger vedlegg ved i e-posten, i sikkerhetsnivå 4 blir det sendt lenke til vedlegget i stedet. 

Om forsendelsen feiler blir det levert error appreceipt tilbake til avsender.

### DPF statuser
 
  
  Denne tabellen viser statusmeldinger der statlig virksomhet med integrasjonspunkt er avsender og kommune / fylkeskommune er mottaker.
  
  | Status | Kommentar |
  | :--- | :--- | 
  | OPPRETTET | Integrasjonspunktet mottar bestEdu-melding fra sak-arkivsystem og oppretter en DPF-melding |
  | SENDT | Integrasjonspunkt har sendt forsendelsen til meldingsformidler |
  | KLAR_FOR_MOTTAK | Venter på at forsendelse skal bli lastet ned av mottaker |
  | LEST | En forsendelse er lest når hele forsendelsesfilen er lastet ned av mottaker | 
  
  Denne tabellen viser statusmeldinger der kommune / fylkeskommune er avsender og statlig virksomhet med integrasjonspunkt er mottaker.
  
  | Status | Kommentar |
  | :--- | :--- |  
  | OPPRETTET | Integrasjonspunkt laster frå KS ned og oppretter meldingen hos seg |
  | INNKOMMENDE_MOTTATT | Meldingen blir konvertert til internt format | 
  | INNKOMMENDE_LEVERT | Meldingen er levert til sak-arkivsystem | 

### DPF relaterte feilstatuser

| Status | Kommentar | 
| :--- | :--- |  
| FEIL | Forsendelse har feilet, les logg for mer informasjon | 
| MANUELT HÅNDTERT | Forsendelsen er manuelt avsluttet, f.eks pga en feilsituasjon |
| AVVIST | Forsendelsen er ikke validert pga. manglende/korrupt metadata, eller fordi forsendelsesfil ikke kunne dannes |
 
 I dette tilfellet blir appreceipt fra sak-arkivsystem discardet, fordi kommuner / fylkeskommuner som avsender ikke kan behandle den.
 
---

### Digital post til virksomheter (DPV)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpv.jpg)

Utgående: Vil bli initiert i sak-arkivsystemet og sendt til integrasjonspunktet. Meldinga vil så bli levert til Altinn sin DPV tjeneste. Appreceipt blir levert til avsender etter at Integrasjonspunktet får "ok" på forsendelserequesten mot Altinn. Leveringskvittering blir levert tilbake til avsender. Meldingen kan hentes i virksomhetens innboks i Altinn. Personen som henter meldinga må ha riktige rettigheter i Altinn for å kunne lese den (feks. sak-arkiv rolle)

Innkommende: Om virksomheten din ikke har tatt i bruk eFormidling vil du få post i innboksen i Altinn. 

Om forsendelsen feiler blir det levert error appreceipt tilbake til avsender.

### DPV statuser
 
 Alt i tabellen under er logget av avsender sitt integrasjonspunkt. Per i dag støtter ikke Altinn DPV lese-bekreftelse.
 
 | Status | Kommentar| 
 | :--- | :--- |
 | OPPRETTET | Integrasjonspunktet mottar bestEdu-melding fra sak-arkivsystem og oppretter en DPV-melding |
 | SENDT | Integrasjonspunkt har sendt forsendelsen til Altinn's DPV tjeneste | 
 | LEVERT | Når DPV-melding er levert til Altinn sin DPV tjeneste blir en appreceipt sendt tilbake og status i sak-arkivsystem blir oppdatert | 
 | LEST | Når mottaker har åpnet og lest meldingen blir status oppdatert til lest (foreløpig ikkje implementert)| 
 

---



### Digital post til innbygger (DPI)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpi.jpg)

Utgående: Vil bli initiert i sak-arkivsystemet og sendt til Posten sin meldingsformidler. Der vil det bli formidlet til innbygger sin digitale postkasse, eBoks eller Digipost. Om innbygger ikke har en digital postkasse vil brevet bli sendt til innboksen i Altinn. Om avsender ønsker det kan det bli sendt til print i stedet for til Altinn. 

DPI via eFormidling bruker NextMove grensesnittet. Det betyr at avsendersystemet ditt må støtte dette. DPI støtter også printtjenesten til Posten.

### DPI statuser

*Kommer*

---

### Mottakende sak-arkivsystem

Når sak-arkivsystem mottar meldinger fra de forskjellige kanalene responderer de litt ulikt. Appreceipts blir sendt ut og statuser blir oppdatert.

#### ePhorte mottar 

DPO: Applikasjonskvittering sendes når melding har kommet inn til mottakers sak-arkivsystem. Dette er per i dag en manuell operasjon og det betyr at det kan ta ekstra tid før avsender blir oppdatert. Det er en utviklingsoppgave å gjøre dette automatisk.

DPF: Når mottaker tar imot posten via SvarInn, sendes ok kvittering når posten importeres (på vei inn til sak). Dette er per i dag en manuell operasjon og det betyr at det kan ta ekstra tid før avsender blir oppdatert. Det er en utviklingsoppgave å gjøre dette automatisk.

DPV: Mottar leveringsbekreftelse umiddelbart etter leveranse til Altinn DPV.  

DPI: har ikke implementert lesebekreftelse. Opp til innbygger å gi lesebekreftelse. Gir difor lite meining å implementere. 

#### P360 mottar 

DPO:  Applikasjonskvittering sendes når melding har kommet inn til mottakers sak-arkivsystem.   

DPF: Sender applikasjonskvittering når melding har kommet inn til mottakers integrasjonspunkt, før import til P360

DPV: Mottar leveringsbekreftelse umiddelbart etter leveranse til Altinn DPV.

#### Websak mottar 

DPO: Applikasjonskvittering sendes når melding har kommet inn til mottakers sak-arkivsystem. 

DPF:  Kvittering sendes når SvarInn har tatt imot. 

DPV: Mottar leveringsbekreftelse umiddelbart etter leveranse til Altinn DPV.

### eInnsyn (DPE)

![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpe.png)

1. Arkivar henter trigger eksport av oep saker
2. Laster opp oep fil til filområde arkivar og eInnsynsklient har tilgang til
3. eInnsynsklient splitter opp oep meldingen til eInnsynsmeldinger,
4. Laster eInnsysnsmelding til integrasjonspunktet
5. Integrasjonspunktet gjør oppslag for å finne mottaker (capability oppslag)
6. Intgrasjonspunktet krypterer, signerer og pakker melding. Laster deretter opp til mottakers kø
7. Ingegrasjonspunktet laster ned nye meldinger fra kø, pakker opp, sjekker signatur, dekrypterer melding, tilgjengeligjør for mottaker
8. eInnsysnsapplikasjon henter meldinger fra integrasjonpunktet, tilgjengeliggjør i eInnsynssøk
9. Person søker innsyn
10. Innsynskrav lastes opp til integrasjonspunkt
11. Integrasjonspunktet gjør oppslag for å finne mottaker (capability oppslag)
12. Integrasjonspunktet krypterer, signerer og pakker melding. Laster deretter opp til mottakers kø
13. Integrasjonspunktet laster ned nye meldinger fra kø, pakker opp, sjekker signatur, dekrypterer melding, tilgjengeliggjør for mottaker
14. eInnsynsklient sender innsynskrav via mottakers mailserver
15. Innsynskrav tilgjengeliggjøres i via mottakers sakarkvisystem/mailserver eller lignende.

---

