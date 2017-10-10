---
title: Installasjon av integrasjonspunkt
pageid: installasjonavintegrasjonspunkt
layout: default
description: Beskrivelse av oppsett av integrasjonspunktet.
isHome: false
---

Einnsyn er en prosess der statelige virksomheter gjør metadata for korrespondanse tilgjengelig for offentligheten. Integrasjonspunktet fungerer som et bindeledd mellom eInnsynsklienten brukt av arkivarene og det sentrale eInnsynsystemet.

For å sette opp integrasjonspunktet til å støtte eInnsyn, må du gjøre følgende: 


## Dette gjør du før installasjon av Integrasjonspunktet

+ Tilgjengelig minne må være minimum 3 ganger større enn størrelsen på meldinger ønsket sendt.
+ Nødvendige brannmuråpninger
+ Java 8 med JCE installert
+ Test virksomhetssertifikat utstedt av Buypass eller Commfides. [Les mer](http://difi.github.io/move-integrasjonspunkt/vStaging/#/4_sertifikat)
+ BestEdu ekspederingskanal skrudd på i sak-/arkivsystem
+ Tips: Installer integrasjonspunktet og eInnsynsklient på samme server.

### 1. Brannmuråpninger for eInnsyn

> Brannmuråpninger i TESTMILJØ

Sentrale tjenester(Adresseoppslag, sentral konfigurasjon mm.) 
+ beta-meldingsutveksling.difi.no -> 93.94.10.30:443, 93.94.10.45:443, 93.94.10.5:443

Meldingsformidler eInnsyn
+ move-dpe.servicebus.windows.net -> *.cloudapp.net

Id-portens autentiseringstjeneste 
+ oidc-ver2.difi.no -> 146.192.252.152:443

Logging 
+ 93.94.10.18:8300

> Brannmuråpninger i PRODUKSJON

Sentrale tjenester(Adresseoppslag, sentral konfigurasjon mm.) 
+ meldingsutveksling.difi.no -> 93.94.10.30:443, 93.94.10.45:443, 93.94.10.5:443

Meldingsformidler eInnsyn
+ move-dpe-prod.servicebus.windows.net -> *.cloudapp.net

Id-portens autentiseringstjeneste 
+ oidc.difi.no -> 146.192.252.54:443

Logging 
+ 93.94.10.18:8400

### 2. Installere Java runtime environment (JRE)

Integrasjonspunktet er en Java applikasjon og krever derfor at man har Java kjøremiljø installert på serveren den skal kjøre.
For å verifisere om java er installert og hvilken versjon kan du i et kommandolinje vindu bruke kommandoen

```
java -version
```

Meldingsformidlingsapplikasjonen krever minimum versjon 1.8.0

Dersom Java ikke er installert eller versjonen er for gammel, kan ny versjon lastes ned [her](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) og installeres.

### 3. Installere Java Cryptography Extension (JCE)

Bruker du ny versjon av Java, må ny JCE installeres. Last ned JCE fra [Oracles sider](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html).

Det er ikke noen enkel måte å sjekke om Java Cryptography Extension er installert. Ofte kan det enkleste være å bare laste ned og installere JCE, men om du ønsker å sjekke, kan du gå til mappen ```$JAVA_HOME/jre/lib/security``` og sjekke om filene ```US_export_policy.jar``` og ```local_policy.jar``` har nyere dato enn øvrige filer. Hvis datoen er lik, må du installere JCE.
Dersom JCE mangler vil integrasjonspunket stoppe under oppstart og skrive logmelding om manglende JCE.

### 4. Virksomhetssertifikat

NB! Testmiljø krever **test virksomhetssertifikat**. Produksjonsertifikat vil ikke virke i test

NB2! Bruk sertifikatet merkt som **Autentiseringssertifikatet**

**Å gjøre:**
1. Anskaffe test virksomhetssertifikat
2. Legge sertifikat i Java Key Store.
3. Sende sertifikat til Difi.

**Hvordan?**

[VEILEDNING: Les alt om håndtering av virksomhetssertifikat her](http://difi.github.io/move-integrasjonspunkt/vStaging/#/4_sertifikat)

Integrasjonspunktet bruker virksomhetssertifikat til kryptering og signering av meldinger som går mellom integrasjonpunkter.
Virksomhetssertifikat som kan benyttes leveres av [Commfides](https://www.commfides.com/e-ID/Bestill-Commfides-Virksomhetssertifikat.html) og [Buypass](http://www.buypass.no/bedrift/produkter-og-tjenester/buypass-virksomhetssertifikat)

***
