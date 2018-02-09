---
title: Installasjonsveiledning for integrasjonpunktet
keywords: sample homepage
tags: [integrasjonspjnkt]
sidebar: veiledning_sidebar
permalink: index.html
summary: These brief instructions will help you get started quickly with the theme. The other topics in this help provide additional information and detail about working with other aspects of this theme and Jekyll.
---

## Installasjonsveiledning




 Tilgjengelig minne må være minimum 3 ganger større enn størrelsen på meldinger ønsket sendt.
+ Nødvendige brannmuråpninger
+ Java 8 med JCE installert
+ Virksomhetssertifikat utstedt av Buypass eller Commfides. [Les mer](http://difi.github.io/move-integrasjonspunkt/vStaging/#/4_sertifikat)
+ BestEdu ekspederingskanal skrudd på i sak-/arkivsystem
+ Tips: Installer integrasjonspunktet og eInnsynsklient på samme server.     


### Bakgrunn 

Integrasjonspunktet er per definisjon grenselinjen mellom det interne og eksterne miljøet. Intensjonen er at denne grenselinjen skal være så enkel som mulig. Erfaring viser at det kan være aktuelt å eksponere enkelte tjenester gjennom integrasjonspunktet. Dette vil være tjenester som i dag medfører kompleksitet for hver enkel leverandør, men der vi alle kan ha stor nytte av at det er en felles løsning. Eksempel på dette kan være tjenester for å bygge en sikker melding (lage ASIC-kontainer) og lokal eksponering av nasjonale tjenester relatert til adressering. En mulig tanke er at man legger alle tjenester som krever virksomhetssertifikat inn i integrasjonspunktet. Hvilke applikasjonstjenester trengs for avsender, og hvor bør de realiseres? Ved mottak av en melding, er det behov for en tilsvarende fordeling av ansvar.

Hvem validerer at meldingen er korrekt?
Hvordan skal denne gjøres tilgjengelig for de løsninger som skal håndtere meldingen lokalt?
Skal vi legge opp til en push eller pull arkitektur, eller begge deler?
Skal vi ha forskjellige strategier for å håndtere meldinger basert på størrelse? (strømmende vs pull baserte grensesnitt).

## Komme igang

For å ta ibruk integrasjonspunktet må du gjennomføre listen under

1. Installere Java 8 med JCE
2. Ansaffe virksomhetssertifikat for test og produksjon
3. Skru på ekspederingskanal for BestEdu i Sak-/Arkivsystemet (Gjelder ikke for eInnsyn)
4. Gjøre lokalt oppsett for integrasjonpunktet
5. Sørg for å holde server i Sync med NTP

