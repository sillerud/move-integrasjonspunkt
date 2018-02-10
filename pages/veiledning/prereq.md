---
title: Forutsetninger for installasjon
description: Forutsetninger for installasjon
summary: "Forutsetninger for installasjon"
permalink: forutsetninger.html
sidebar: veiledning_sidebar
folder: veiledning
---

Einnsyn er en prosess der statelige virksomheter gjør metadata for korrespondanse tilgjengelig for offentligheten. Integrasjonspunktet fungerer som et bindeledd mellom eInnsynsklienten brukt av arkivarene og det sentrale eInnsynsystemet.

For å sette opp integrasjonspunktet til å støtte eInnsyn, må du gjøre følgende: 


## Dette gjør du før installasjon av Integrasjonspunktet

+ Tilgjengelig minne må være minimum 3 ganger større enn størrelsen på meldinger ønsket sendt.
+ Nødvendige brannmuråpninger
+ Java 8 med JCE installert
+ Virksomhetssertifikat utstedt av Buypass eller Commfides. [Les mer](http://difi.github.io/move-integrasjonspunkt/vStaging/#/4_sertifikat)
+ BestEdu ekspederingskanal skrudd på i sak-/arkivsystem
+ Tips: Installer integrasjonspunktet og eInnsynsklient på samme server.

### Tidssynkronisering
Bekreftelse på at en bruker er autentisert sendes fra OIDC til innholdsleverandør sitt integrasjonspunkt i form av en Json Web Token (JWT). En JWT inneholder tidsstempel som angir hvor lenge den er gyldig. Det er derfor viktig at alle servere som kommuniserer via integrasjonspunktet har synkroniserte klokker. OIDC bruker NTP (”network time protocol”) for synkronisering, tidskilden er GPS-basert. Det er videre viktig at alle servere i CoT er justert korrekt for tidssone og sommertid. (CET / CEST i Norge). Du kan sjekke din klokke her: https://time.is/ 

[For mer informasjon om Network Time Protocol.](https://no.wikipedia.org/wiki/Network_Time_Protocol)
Tjenesteleverandør velger selv tidskilde, denne bør være lokalisert internt i datasenteret.

### Brannmuråpninger i testmiljø

Når du installerer den typen eFormidling du skal ta i bruk så må du åpne opp noen brannmuråpninger. I akkordion lenger nede så må du åpne både generelle og spesifikke for den tjenesten du skal installere.

<button data-toggle="collapse" data-target="#demo">Brannmuråpninger testmiljø: DPE</button>
<div id="demo" class="collapse">
  {% include custom/firewall_staging/staging_generell.html %} 
  {% include custom/firewall_staging/staging_dpe.html %}
</div>

<button data-toggle="collapse" data-target="#demo2">Brannmuråpninger testmiljø: DPO og DPV</button>
<div id="demo2" class="collapse">
 {% include custom/firewall_staging/staging_generell.html %} 
  {% include custom/firewall_staging/staging_dpo.html %}
</div>

<button data-toggle="collapse" data-target="#demo3">Brannmuråpninger testmiljø: DPI</button>
<div id="demo3" class="collapse">
  {% include custom/firewall_staging/staging_generell.html %} 
  {% include custom/firewall_staging/staging_dpi.html %}
</div>

Random text why not u work

### Brannmuråpninger i produksjon

<button data-toggle="collapse" data-target="#demo4">Brannmuråpninger testmiljø: DPE</button>
<div id="demo4" class="collapse">
  {% include custom/firewall_prod/prod_generell.html %} 
  {% include custom/firewall_prod/prod_dpe.html %}
</div>

<button data-toggle="collapse" data-target="#demo5">Brannmuråpninger testmiljø: DPO og DPV</button>
<div id="demo5" class="collapse">
  {% include custom/firewall_prod/prod_generell.html %} 
  {% include custom/firewall_prod/prod_dpo.html %}
</div>

<button data-toggle="collapse" data-target="#demo6">Brannmuråpninger testmiljø: DPI</button>
<div id="demo6" class="collapse">
  {% include custom/firewall_prod/prod_generell.html %} 
  {% include custom/firewall_prod/prod_dpi.html %}
</div>

### Installere Java runtime environment (JDK)

Integrasjonspunktet er en Java applikasjon og krever derfor at man har Java kjøremiljø installert på serveren den skal kjøre.
For å verifisere om java er installert og hvilken versjon kan du i et kommandolinje vindu bruke kommandoen

```
java -version
```

Meldingsformidlingsapplikasjonen krever minimum versjon 1.8.0

Dersom Java ikke er installert eller versjonen er for gammel, kan ny versjon lastes ned [her](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) og installeres.

### Installere Java Cryptography Extension (JCE)

Bruker du ny versjon av Java, må ny JCE installeres. Last ned JCE fra [Oracles sider](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

Det er ikke noen enkel måte å sjekke om Java Cryptography Extension er installert. Ofte kan det enkleste være å bare laste ned og installere JCE, men om du ønsker å sjekke, kan du gå til mappen ```$JAVA_HOME/jre/lib/security``` og sjekke om filene ```US_export_policy.jar``` og ```local_policy.jar``` har nyere dato enn øvrige filer. Hvis datoen er lik, må du installere JCE.
Dersom JCE mangler vil integrasjonspunket stoppe under oppstart og skrive logmelding om manglende JCE. På nyere Java versjoner må en legge JCE-filene inn i både ```$JAVA_HOME/jre/lib/security/unlimited ``` og ```$JAVA_HOME/jre/lib/security/limited```.

### Virksomhetssertifikat

> * NB! Testmiljø krever **test virksomhetssertifikat**. Produksjonsertifikat vil ikke virke i test
> * NB2! Produksjonsmiljøet krever virksomhetssertifikat for produksjon. 
> * NB3! Bruk sertifikatet merkt som **Autentiseringssertifikatet**

**Å gjøre:**
1. Anskaffe virksomhetssertifikat
2. Legge sertifikat i Java Key Store.
3. Sende sertifikat til Difi.

**Hvordan?**

[VEILEDNING: Les alt om håndtering av virksomhetssertifikat her](..)

Integrasjonspunktet bruker virksomhetssertifikat til kryptering og signering av meldinger som går mellom integrasjonpunkter.
Virksomhetssertifikat som kan benyttes leveres av [Commfides](https://www.commfides.com/e-ID/Bestill-Commfides-Virksomhetssertifikat.html) og [Buypass](http://www.buypass.no/bedrift/produkter-og-tjenester/buypass-virksomhetssertifikat)

***