---
title: Konfigurere for kommunikasjon med kommunesektorens FIKS plattform (DPF)
description: Hvordan man opprette svarinn og svarut-bruker
summary: "Hvordan man opprette svarinn og svarut-bruker"
sidebar: veiledning_sidebar
permalink: ksfiks.html
folder: veiledning
---

# Konfigurering av DPF – Kobling av eFormidling til KS-FIKS 

> NB! Denne delen av veiledningen er basert på dokumentasjon delt av Fylkesmannen i Nordland. Det er viktig at dere følger den i detalj for å få opprette brukernavn og passord til SvarUt og SvarInn.  Dette er input til konfigurering av lokalt integrasjonspunkt, og legges inn i integrasjonspunkt-local.properties-filen.

Ta kontakt på <a href="mailto:idporten@difi.no">idporten@difi.no</a> dersom dere har spørsmål til dokumentasjon eller trenger støtte underveis.

Ved spørsmål rundt KS SvarUt eller SvarInn ta kontakt med KS på e-post: <a href="mailto:svarut@ks.no">svarut@ks.no</a>

### Dette er punktene man må gå gjennom for å opprette kobling fra sak-arkivsystemet til KS-FIKS: 

I Altinn må dere delegere post/arkiv rolle til de som skal ha tilgang til KS SvarUt-forvaltning. Denne rollen må man også inneha for å manuelt kunne laste ned SvarUt sendinger. 
 
For informasjon om delegering av post/arkiv-rolle i Altinn, kontakt med <a href="mailto:servicedesk@altinn.no">servicedesk@altinn.no</a>

### Knytte virksomheten til KS-FIKS.  
For å kunne konfigurere KS SvarUt-forvaltning må avtale med KS være på plass. [Slik komme du i gang med avtalen - se steg 2](https://samarbeid.difi.no/eformidling/hvordan-ta-i-bruk-eformidling). Nedenfor får du informasjon om stegene en må igjennom ved konfigurering av KS SvarUt. 

- Konfigurasjon for mottak: SvarInn 
  - legg inn alle organisasjonsnumre til virksomheten, også underenheter 
  - brukernavn og servicepassord (difi.move.fiks.inn.username og password i integrasjonspunkt-local.properties-filen) 
- konfigurasjon for sending: SvarUt 
  - Fakturaopplysninger 
  - brukernavn og servicepassord (difi.move.fiks.ut.username og password i integrasjonspunkt-local.properties-filen)  
 
### Hva er KS-FIKS og hvorfor er det så viktig i eFormidling? 

KS-FIKS sørger for at virksomheter mottar elektronisk post sendt med SvarUt i sak/arkiv systemet.

KS-FIKS sørger også for at meldinger fra sak/arkiv system kan mottas av SvarInn-brukere i sitt sak/arkiv system. 

Post til virksomheter fra sak/arkiv system består til slutt av Digital post til offentlige virksomheter, Digital post til virksomheter(DPV), kommuner og fylkeskommuner (KS-FIKS). Sammen med digital postkasse til innbyggere (kommer til eFormidling ila sommeren 2018) har man så en helhetlig løsning 
for digital forsendelse i sak/arkiv system som gjør det mulig å sende elektronisk til innbyggere og virksomheter. 
 
### Hvordan knytte seg til KS-FIKS? 

KS, Difi og KMD er blitt enige om at alle statlige virksomheter nå kan koble seg til KS-FIKS.  
Avtalen må fylles ut, signeres og sendes i retur til KS. Administrator må oppgis. Denne personen vil bli lagt inn med tilgang til forvaltningsgrensesnittet KS SvarUt-forvalting. [Hvordan komme i gang med svarut](http://www.ks.no/fagomrader/utvikling/digitalisering/svarut/komme-i-gang-med-svarut/)  

Skal administrator ha mulighet til manuell nedlastning av SvarUt-sendinger, må vedkommende ha post/arkiv-rolle i Altinn for virksomheten.  

**For å kunne opprette integrasjonen til KS-FIKS må 2 sett med brukernavn og passord opprettes; et for SvarInn og et for SvarUt. Disse må legges inn i integrasjonspunkt-local.properties-filen.** [Se her for nærmere informasjon.](https://difi.github.io/moveintegrasjonspunkt/properties_config.html#digital-post-til-virksomheter) 
 
Etter integrasjonen er etablert vil SvarUt sendinger komme frem til sak/arkiv systemet. 
 
  
### Konfigurering av KS SvarUt-forvaltning 

Forvaltningsgrensesnittet som administrator får tilgang til (informeres om av KS) er laget for kommuner og andre som tar i bruk alle funksjonene i SvarUt og SvarInn.  

KS vil sende påloggingsadresse til administrator etter at virksomheten er opprettet som bruker. Etter pålogging har administrator forskjellige valg: 
 
![bilde1](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/01_FIKS.png?raw=true)
 
NB: Alle eksemplene og skjermbildene er hentet fra oppsettet til FM Nordland. Det gjelder tilsvarende for din virksomhet – bare med deres eget navn og organisasjonsnummer. Det er bare opplysninger som nevnes heretter må utfylles i konfigureringen. 

1. Start med «konfigurasjon» (konfigurerer SvarUt-funksjonene) 

**Klikk på «overordnet organisasjon» og legg inn fakturaopplysninger:**

![bilde2](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/02_FIKS.png?raw=true)
 
**Klikk så på «underordnet» organisasjonsnivå**
 
![bilde3](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/03_FIKS.png?raw=true) 
 
De menypunktene som du ser markert med grønn hake skal du klikke deg gjennom og fylle ut: 
- **ReturAdresse/forside: Legg inn organisasjonsnummer og adresse. Forsidetekst må ikke fylles ut.** 
- **Altinn: Velg «ingen varsling»** 
 
![bilde4](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/04_FIKS.png?raw=true)
 
- **Print: Velg «manuell print»** 

![bilde5](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/05_FIKS.png?raw=true)
 
- **Servicepassord: Dette er passord nr. 1 dere må notere dere. Passord generes ved å klikke på «generer nytt servicepassord». Pass på at du ikke endrer dette senere ved å klikke på «generer» på nytt!**
 
![bilde6](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/06_FIKS.png?raw=true) 
 
- **Tilganger: Her kan administrator legge til flere brukere som skal ha administratorrettigheter i SvarUt-forvaltning. Sett i så fall hake i alle tilganger.** 

![bilde7](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/07_FIKS.png?raw=true)
 
Dere ser bort fra punktene SDP, Edialog og Admin. 
 
### Fortsett med konfigurering av «mottakersystem» (konfigurerer SvarInn-funksjonene) 

**Klikk på «mottakersystem» og organisasjonen din** 
 
![bilde8](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/08_FIKS.png?raw=true) 
 
- **Service: Dette er passord nr. 2 dere må notere dere. Passord generes ved å klikke på «generer nytt servicepassord». Pass på at du ikke endrer dette senere ved å klikke på «generer» på nytt!**  
 
![bilde9](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/09_FIKS.png?raw=true)
 
- **Administrasjon: Skal være utfylt på forhånd. Sjekk at e-postadressene er lagt inn rett. De brukes til varsel om driftsproblemer eller hvis KS vil varsle dere om noe de fanger opp som avvik.** 

![bilde10](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/10_FIKS.png?raw=true)
 
- **Tilganger: Også her kan det legges til andre brukere.**
- **Organisasjoner: Her legger du inn organisasjonsnummer til virksomheten og eventuelle underenheter. Dette sørger for at elektronisk SvarUt-post sendt til organisasjonsnummer til en underenhet også blir importert til sak/arkiv systemet. Hvis man ikke gjør dette vil slik post fortsette å komme til Altinn. Oversikt over underenhetene og organisasjonsnumre finner du i Altinn.**

Man må vente med å legge inn organisasjonsnumrene til konfigurasjon av integrasjonspunktet er utført, fordi SvarUt vil prøve å sende til integrasjonen når numrene er lagt inn. De kan bare legges inn av administrator som har gyldig post/arkiv-rolle for alle organisasjonsnumrene.  
 
![bilde11](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/11_FIKS.png?raw=true)


---

