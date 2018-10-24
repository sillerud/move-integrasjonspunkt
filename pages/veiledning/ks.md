---
title: Konfigurering av DPF – for kommunikasjon på tvers av kommunal og statlig sektor
description: Hvordan man opprette svarinn og svarut-bruker
summary: "Hvordan man opprette svarinn og svarut-bruker"
sidebar: veiledning_sidebar
permalink: ksfiks.html
folder: veiledning
---

### Hvordan henger eFormidling og KS sin FIKS plattform sammen?

KS, Difi og KMD er blitt enige om at alle statlige virksomheter nå kan kommunisere med kommunal sektor via eFormidling.

eFormidling og kommunene sin digitale plattform FIKS snakker sammen. Statlige virksomheter benytter <a href="mailto:idporten@difi.no">idporten@difi.no</a> som kontaktpunkt. Avsender bruker sitt sak/arkiv-system, sender dokument via eFormidling og kommunen mottar dokumentet via SvarInn. Eksempelvis kan statlige virksomheter (som bruker eFormidling) kommunisere med kommuner (som bruker KS FIKS-plattformen) helt sømløst på en enkel og sikker måte. Difi koordinerer med KS og ber de oversende avtaleverk med formål eFormidling til aktuell kontaktperson hos deg. Se [Steg 2 her](https://samarbeid.difi.no/eformidling/hvordan-ta-i-bruk-eformidling)
                                                       
Avtalen må fylles ut, signeres og sendes i retur til KS. Administrator må oppgis. Denne personen vil bli lagt inn med tilgang til forvaltningsgrensesnittet KS SvarUt-forvalting. 

Etter integrasjonen er etablert vil SvarUt sendinger til deg, komme til ditt  sak/arkiv system, og kommuner som abonnerer på tjenesten SvarInn vil motta sending fra deg i sitt sak-/arkiv system. . 

### Avtaleverk – KS

Dersom virksomheten din skal ta i bruk hele eFormidling må dere inngå bruksvilkår for Difis fellesløsninger. Les mer om eFormidling og bruksvilkår på samarbeid.difi.no.

For å ta i bruk FIKS-plattformen må KS sin avtale fylles ut, signeres og sendes til KS. Dere må oppgi en administrator. Denne personen vil få tilgang til forvaltningsgrensesnittet KS SvarUt-forvaltning.

### Hvorfor skal vi sette opp tjenesten DPF i eFormidling?

Fordi det effektiviserer utveksling av meldinger på tvers av stat og kommune:
-	post sendt via SvarUt kommer rett inn til ditt sak-/arkiv system. 
-	post fra dere mottas i sak-/arkiv system hos kommuner/fylkeskommuner som har tjenesten SvarInn fra KS. 
-	vi sikrer og standardiserer hvordan vi sender og mottar digital post.
-	vi respekterer mottaker sin valgte kanal for å ta imot post digitalt. 

I tillegg bidrar vi til samhandling på tvers av offentlig sektor, Difi anbefaler derfor at statlige virksomheter inngår avtale med KS med formål eFormidling.

### Hvorfor må både stat og kommune fortsatt ha rutiner for å hente post i Altinn?

Post fra kommuner med eldre versjon av SvarUt, mangler en del metadata, og derfor vil post fra disse enda havne i din Altinn. Mottar du post fra statlige virksomheter i Altinn? Kontakt avsender, og oppfordre dem til å ta i bruk eFormidling. 

Kommuner og fylkeskommuner som ikke abonnerer på tjenesten SvarInn hos KS, må fortsatt hente post sendt via eFormidling hos Altinn. Det er derfor fortsatt viktig at alle virksomheter sørger for gode rutiner for å hente posten i Altinn.

Viktig informasjon! 
De som skal hente post i Altinn på vegne av virksomheten, må ha fått delegert rolle post/arkiv i Altinn. [Les mer her](https://www.altinn.no/hjelp/profil/roller-og-rettigheter/hvordan-gi-rettigheter-til-andre/) eller <a href="mailto:servicedesk@altinn.no">servicedesk@altinn.no</a>. 

### Hva skal til for at eFormidling snakker med den kommunale FIKS plattformen?

1.	Dere har signert avtale med KS (Slik kommer du i gang med avtalen - [se steg 2.](https://samarbeid.difi.no/eformidling/hvordan-ta-i-bruk-eformidling))  
2.	Systemadministrator får deretter tildelt bruker i FIKS, og logger inn med tildelt brukernavn og passord som mottas på sms.
3.	Følg prosessen i neste kapittel for å generere brukernavn og passord for SvarUt og SvarInn (to sett brukernavn og passord). 

Disse to blir input i konfigurering av lokalt integrasjonspunkt. To sett brukernavn og passord legges inn i integrasjonspunkt-local.properties-filen. Utføres av teknisk ressurs.

Det er avgjørende at arkivfaglig ressurs og teknisk ressurs samarbeider godt i denne prosessen.
Noen virksomheter har slik ressurs internt, mens andre har egen driftsleverandør. Uansett er det virksomheten sitt ansvar å påse at nødvendig teknisk ressurs er tilgjengelig for å utføre konfigurering av lokalt integrasjonspunkt iht dokumentasjon. 

Disse genererer dere ved å logge på FIKS og konfigurere SvarUt og SvarInn.

### Hva må vi gjøre for å bruke eFormidling og FIKS-plattformen?

Forutsett at dere har akseptert bruksvilkår for Difis fellesløsninger, må følgende steg gjennomføres:

1.	Signer avtale med KS (Slik komme du i gang med avtalen - [se steg 2.](https://samarbeid.difi.no/eformidling/hvordan-ta-i-bruk-eformidling))  
2.	Systemadministrator som får tildelt bruker i FIKS logger inn med tildelt brukernavn og passord. Passord mottar dere på SMS.
3.	Følg prosessen i neste kapittel for å generere brukernavn og passord for SvarUT og SvarInn. NB! Dette er to sett med brukernavn og passord. Brukernavn og passord blir input i konfigurering av lokalt integrasjonspunkt og legges inn in integrasjonspunkt-local.properties-filen. Dette utføres av teknisk ressurs.

#### Viktig samarbeid mellom tekniske og arkivfaglige ressurser

Det er viktig at både arkivfaglige og tekniske ressurser samarbeider godt i prosessen om å ta i bruk eFormidling og FIKS-plattformen. Noen virksomheter har ressurser internt, andre benytter seg av driftsleverandør. Det er virksomheten sitt ansvar å påse at nødvendig teknisk ressurs er tilgjengelig for å utføre konfigureringen av lokalt integrasjonspunkt i henhold til dokumentasjon.

### Hvordan generere brukernavn og passord for SvarUt og SvarInn - steg for steg

Følg stegene under nøye, for å fullføre konfigurering i FIKS.
- Konfigurasjon for mottak: SvarInn 
  - legg inn alle organisasjonsnumre til virksomheten, også underenheter
  - brukernavn og servicepassord (difi.move.fiks.inn.username og password i integrasjonspunkt-local.properties-filen)
- Konfigurasjon for sending: SvarUt 
  - Fakturaopplysninger
  - brukernavn og servicepassord (difi.move.fiks.ut.username og password i integrasjonspunkt-local.properties-filen)

Under finner du veiledning med skjermbilder, delt av Fylkesmannen i Nordland.

  
### Konfigurering av KS SvarUt-forvaltning 

Forvaltningsgrensesnittet som administrator får tilgang til (informeres om av KS) er laget for kommuner og andre som tar i bruk alle funksjonene i SvarUt og SvarInn.  

KS vil sende påloggingsadresse til administrator etter at virksomheten er opprettet som bruker. Etter pålogging har administrator forskjellige valg: 
 
![bilde1](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/01_FIKS.png?raw=true)
 
NB: Alle eksemplene og skjermbildene er hentet fra oppsettet til FM Nordland. Det gjelder tilsvarende for din virksomhet – bare med deres eget navn og organisasjonsnummer. Det er bare opplysninger som nevnes heretter må utfylles i konfigureringen. 

---

1. Start med «konfigurasjon» (konfigurerer SvarUt-funksjonene) 

**Klikk på «overordnet organisasjon» og legg inn fakturaopplysninger:**

![bilde2](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/02_FIKS.png?raw=true)
 
**Klikk så på «underordnet» organisasjonsnivå**

Menypunktene du ser markert med grønn hake skal du klikke deg gjennom og fylle ut. 
 
![bilde3](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/03_FIKS.png?raw=true) 
 

### ReturAdresse/forside
**ReturAdresse/forside: Legg inn organisasjonsnummer og adresse. Forsidetekst må ikke fylles ut.** 

### Altinn
**Altinn: Velg «ingen varsling»** 
 
![bilde4](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/04_FIKS.png?raw=true)

### Print
**Print: Velg «manuell print»** 

![bilde5](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/05_FIKS.png?raw=true)

### Servicepassord
**Dette er passord nr. 1 dere må notere dere. Passord generes ved å klikke på «generer nytt servicepassord». Pass på at du ikke endrer dette senere ved å klikke på «generer» på nytt!**
 
![bilde6](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/06_FIKS.png?raw=true) 

### Tilganger
**Her kan administrator legge til flere brukere som skal ha administratorrettigheter i SvarUt-forvaltning. Sett i så fall hake i alle tilganger.** 

![bilde7](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/07_FIKS.png?raw=true)
 
Dere ser bort fra punktene SDP, Edialog og Admin. 
 
### Fortsett med konfigurering av «mottakersystem» (konfigurerer SvarInn-funksjonene) 

**Klikk på «mottakersystem» i menyen på toppen og så på organisasjonen din i menyen på venstre side** 

 
![bilde8](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/08_FIKS.png?raw=true) 

---

### Service
Velg så service.

**Dette er passord nr. 2 dere må notere dere. Passord generes ved å klikke på «generer nytt servicepassord». Pass på at du ikke endrer dette senere ved å klikke på «generer» på nytt!**  

#### Offentlig nøkkel / virksomhetssertifikat
**Her må en også laste opp den offentlige-nøkkelen til et virksomhetssertifikatet, det kan godt være den samme som integrasjonspunktet bruker.**
 
![bilde9](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/09_FIKS2.PNG?raw=true)
 
 ### Administrasjon
**Dette feltet skal være utfylt på forhånd. Sjekk at e-postadressene er lagt inn rett. De brukes til varsel om driftsproblemer eller hvis KS vil varsle dere om noe de fanger opp som avvik.** 

![bilde10](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/10_FIKS.png?raw=true)
 
 ### Tilganger
**Også her kan det legges til andre brukere.**

### Organisasjoner
**Her legger du inn organisasjonsnummer til virksomheten og eventuelle underenheter. Dette sørger for at elektronisk SvarUt-post sendt til organisasjonsnummer til en underenhet også blir importert til sak/arkiv systemet. Hvis man ikke gjør dette vil slik post fortsette å komme til Altinn. Oversikt over underenhetene og organisasjonsnumre finner du i Altinn.**

> merk! Per i dag støtter ikke eFormidling forsendelser fra underenheter, kun til orgnummeret som er registrert i integrasjonspunktet. Så forsendelser til Svarinn på underorgnummer må hentes manuelt i Svarinn. Funksjonalitet for dette vil komme i Nextmove grensesnittet som er under arbeid.

Man må vente med å legge inn organisasjonsnumrene til konfigurasjon av integrasjonspunktet er utført, fordi SvarUt vil prøve å sende til integrasjonen når numrene er lagt inn. De kan bare legges inn av administrator som har gyldig post/arkiv-rolle for alle organisasjonsnumrene.  
 
![bilde11](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/11_FIKS.png?raw=true)


---

