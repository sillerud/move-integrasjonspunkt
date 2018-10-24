---
title: Konfigurering av integrasjonspunkt-local.properties
description: Konfigurering av integrasjonspunkt-local.properties-filen.
summary: "Konfigurering av integrasjonspunkt-local.properties-filen."
permalink: properties_config.html
sidebar: veiledning_sidebar
folder: veiledning
---

Denne delen av veiledningen er delt opp slik at du først finner litt generell informasjon før du deretter finner eksempler på integrasjonspunkt-local.properties oppsett spesifikt for den tjenesten du skal ta i bruk. 

### Anbefalt rekkefølge for installasjon av eFormidling

Vi anbefaler alle som skal konfigurere integrasjonspunktet for å ta i bruk eFormidling om å sette opp konfigurasjonen i følgende rekkefølge.

1. Minimumskonfigurasjon for å få starte integrasjonspunktet. 
2. Konfigurere sak-arkivsystem til å prate med integrasjonspunktet
3. Konfigurere DPO innstillinger (brukernavn og passord) 
4. Konfigurere DPV/DPF/DPI innstillinger

Vi anbefaler dere å konfigurere DPO før DPV/DPF/DPI for å unngå å motta post fra svarUt til virksomhetens SvarInn innboks. Ved å konfigurere DPO først vil dere motta post i sak-arkivsystemet

> husk å melde fra til <a href="mailto:idporten@difi.no">idporten@difi.no</a> når dere har konfigurert slik at Difi kan åpne opp tilganger.


### integrasjonspunkt-local.properties

Her laster du ned [integrasjonspunkt-local.properties-filen](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/integrasjonspunkt_local.properties) Per i dag så benytter vi Java Key Store (JKS). Vi jobber med en virtuell HSM-løsning som alternativ til JKS. Vi har valgt å pensjonere Windows Certificate Store løsningen fordi den ikke støtter alle former for eFormidling. Om du allerede bruker WCS og trenger støtte, ta kontakt med <a href="mailto:idporten@difi.no">idporten@difi.no</a>. 

1. Start med å opprette en mappe med navn integrasjonspunkt på for eksempel c:\
2. Last så ned integrasjonspunkt-local.properties filen. den kan lastes ned [her ](https://github.com/difi/move-integrasjonspunkt/blob/gh-pages/resources/integrasjonspunkt_local.properties) og lagre i overnevnte mappe
3. last ned integrasjonspunkt[versjonsnummer].jar filen. Den finner du [her](https://beta-meldingsutveksling.difi.no/service/local/artifact/maven/redirect?r=staging&g=no.difi.meldingsutveksling&a=integrasjonspunkt&v=1.7.82-SNAPSHOT)

Når du er ferdig skal strukturen på området se slik ut:
```
c:/
|-- integrasjonspunkt/
   |-- integrasjonspunkt-local.properties
   |-- integrasjonspunkt[versjon].jar
```

> i integrasjonspunkt-local.properties-filen må du fjerne bortkommentering for den typen eformidling du skal bruke.
> keystore.alias er case-sensitivt

**NB:** Benytt skråstrek (/) eller dobbel omvendt skråstrek (\\\\) som ressursdeler når dere angir filbaner.

Eksempler på konfigurering finner du lenger nede under hver enkelt tjeneste.

### eInnsyn 


<button data-toggle="collapse" data-target="#demo1">Trykk her</button>
<div id="demo1" class="collapse">
  {% include custom/properties/jks_generell.html %} 
</div>

### Digital post til innbyggere

<button data-toggle="collapse" data-target="#demo3">Trykk her</button>
<div id="demo3" class="collapse">
  {% include custom/properties/jks_generell.html %} 
  {% include custom/properties/dpi.html %}
</div>

### Digital Post til virksomheter

Når en virksomhet sender digital post til virksomheter kan virksomheten sende både til andre virksomheter som har et integrasjonspunkt, og til virksomheter som ikke har. 

> NB! For å kople sak-arkivsystemet til integrasjonspunktet for DPV-forsendelser så må DPO være aktivert. ```difi.move.feature.enableDPO=true``` 

> Om du **ikke** bruker einnsyn må du i tillegg legge inn ```difi.move.feature.enableDPE=false```

<button data-toggle="collapse" data-target="#demo6">Trykk her</button>
<div id="demo6" class="collapse">
  {% include custom/properties/jks_generell.html %} 
  <p> Brukernamnet og passordet for difi.move.dpv.username og difi.move.dpv.password er tjenesteeier påloggingen mot Altinn.  <a href="https://altinn.github.io/docs/guides/digital-post-til-virksomheter/">Mer info her</a></p>
  {% include custom/properties/dpv.html %}
</div>

### Hvordan opprette brukere?

[Denne delen er flyttet](https://difi.github.io/move-integrasjonspunkt/create_users.html#opprette-dpo-bruker-altinn-formidlingstjeneste)

--- 
