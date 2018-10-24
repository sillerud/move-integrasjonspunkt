---
title: Konfigurering av DPF – for kommunikasjon på tvers av kommunal og statlig sektor
description: Hvordan man konfigurerer SvarInn og SvarUt-brukernamn/passord mm.
summary: "Hvordan man konfigurerer SvarInn og SvarUt-brukernamn/passord mm."
sidebar: veiledning_sidebar
permalink: ksfiks.html
folder: veiledning
---

Når beskrivelsen under er utført må brukernamn/passord for både SvarUt og SvarInn legges inn i integrasjonspunkt-local.properties. I tillegg til alt som er beskrevet i [tabellen her under "Spesifikk for Digital post til kommuner (DPF)"](https://difi.github.io/move-integrasjonspunkt/properties_config.html#digital-post-til-virksomheter). 

[Se Samarbeidsportalen for alt om avtaler, begrunnelser, sammenhenger og lignende ikke teknisk](https://samarbeid.difi.no/felleslosninger/eformidling)

# Konfigurering av KS SvarUt-forvaltning(avsender)

Forvaltningsgrensesnittet som administrator får tilgang til (informeres om av KS) er laget for kommuner og andre som tar i bruk alle funksjonene i SvarUt og SvarInn.  

KS vil sende påloggingsadresse til administrator etter at virksomheten er opprettet som bruker. Etter pålogging har administrator forskjellige valg: 
 
![bilde1](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/01_FIKS.png?raw=true)
 
> Alle eksemplene og skjermbildene er hentet fra oppsettet til FM Nordland. Det gjelder tilsvarende for din virksomhet – bare med deres eget navn og organisasjonsnummer. Det er bare opplysninger som nevnes heretter må utfylles i konfigureringen. 

---

1. Start med «konfigurasjon» (konfigurerer SvarUt-funksjonene) 

**Klikk på «overordnet organisasjon» og legg inn fakturaopplysninger:**

![bilde2](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/02_FIKS.png?raw=true)
 
**Klikk så på «underordnet» organisasjonsnivå**

Menypunktene du ser markert med grønn hake skal du klikke deg gjennom og fylle ut. 
 
![bilde3](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/03_FIKS.png?raw=true) 
 

### ReturAdresse/forside
**Legg inn organisasjonsnummer og adresse. Forsidetekst må ikke fylles ut.** 

### Altinn
**Velg «ingen varsling»** 
 
![bilde4](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/04_FIKS.png?raw=true)

### Print
**Velg «manuell print»** 

![bilde5](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/05_FIKS.png?raw=true)

### Servicepassord
**Dette er passord nr. 1 dere må notere dere. Passord generes ved å klikke på «generer nytt servicepassord». Pass på at du ikke endrer dette senere ved å klikke på «generer» på nytt!**
 
![bilde6](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/06_FIKS.png?raw=true) 

### Tilganger
**Her kan administrator legge til flere brukere som skal ha administratorrettigheter i SvarUt-forvaltning. Sett i så fall hake i alle tilganger.** 

![bilde7](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/07_FIKS.png?raw=true)
 
Dere ser bort fra punktene SDP, Edialog og Admin. 

--- 

# Konfigurering av SvarInn (mottakersystem)

**Klikk på «mottakersystem» i menyen på toppen og så på organisasjonen din i menyen på venstre side** 

 
![bilde8](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/08_FIKS.png?raw=true) 

---

### Service
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

> Per i dag støtter ikke eFormidling forsendelser fra underenheter, kun til orgnummeret som er registrert i integrasjonspunktet. Så forsendelser til Svarinn på underorgnummer må hentes manuelt i Svarinn. Funksjonalitet for dette vil komme. Etter planen vil dette være første kvartal 2019.

Man må vente med å legge inn organisasjonsnumrene til konfigurasjon av integrasjonspunktet er utført, fordi SvarUt vil prøve å sende til integrasjonen når numrene er lagt inn. De kan bare legges inn av administrator som har gyldig post/arkiv-rolle for alle organisasjonsnumrene.  
 
![bilde11](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/11_FIKS.png?raw=true)


---

