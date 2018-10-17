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

> NB! For å kople sak-arkivsystemet til integrasjonspunktet for DPV-forsendelser så må DPO være aktivert. 

> Om du **ikke** bruker einnsyn må du i tillegg legge inn ```difi.move.feature.enableDPE=false```

<button data-toggle="collapse" data-target="#demo6">Trykk her</button>
<div id="demo6" class="collapse">
  {% include custom/properties/jks_generell.html %} 
  <p> Brukernamnet og passordet for difi.move.dpv.username og difi.move.dpv.password er tjenesteeier påloggingen mot Altinn.  <a href="https://altinn.github.io/docs/guides/digital-post-til-virksomheter/">Mer info her</a></p>
  {% include custom/properties/dpv.html %}
</div>

### Opprette bruker til Altinn formidlingstjeneste (DPO)

(Gjelder bare for digital post til offentlige virksomheter)
Integrasjonspunktet kjører som [datasystem](https://www.altinn.no/no/Portalhjelp/Datasystemer/) mot AltInn's meldingsformidler. Integrasjonspunktet må registeres som et datasystem AltInn's portal. Informasjon om hvordan dette gjøres finnes [her](https://www.altinn.no/no/Portalhjelp/Datasystemer/Registrere-datasystem/). En person som representerer virksomheten må logge inn på Altinn for å gjøre dette.

Når du oppretter datasystemet er det viktig at det gjøres av person som kan representere virksomheten. Hvordan man representerer virksomheten kan du lese [her](https://www.altinn.no/no/Portalhjelp/Hvordan-representere-andre/).

Under opprettelse av datasystem velger du passord og får tildelt brukerid (ID), disse skal senere brukes i properties filen som beskrives lenger nede.

Eksempel: (her kan du gi datasystemet akkuratt det navnet du ønsker. Vi har valgt å kalle den "move".) Kall den gjerne eformidling_dittOrganisasjonsnummer eller annet valgfritt navn.

Registrere datasystem
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/altinnDatasystemRegistrer.PNG "registere datasystem i altinn")


Datasystem registrert:


![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/altinnDatasystemRegistrert.PNG "datasystem registrert")

___

Informasjon om hvordan du logger på AltInn portal finner du <a href="https://www.altinn.no/hjelp/innlogging/">https://www.altinn.no/hjelp/innlogging/</a>.

### Opprette bruker til KS FIKS (Svarut & Svarinn (DPF))

1.	Fortell Difi at du vil kommunisere med kommuner og andre som benytter KS sin FIKS-plattform.
   -	Ref steg 1 av 3 -  https://samarbeid.difi.no/eformidling/hvordan-ta-i-bruk-eformidling
2.	Difi kontakter KS og ber om at aktuelle avtaledokument blir tilsendt aktuell kontaktperson hos deg.
3.	Du sørger for at avtalen blir signert i din virksomhet, skanner og sender tilbake til <a href="svarut@ks.no">svarut@ks.no</a>
   -	Viss du informerer <a href="idporten@difi.no">idporten@difi.no</a> at avtale med KS er signert, kan vi følge framdrift i saken. 
4.	Lokal administrator* oppgir fullt fødselsnummer til KS for å få opprettet bruker i FIKS.
   -	Ring eller send sms til 906 53 432, Steinar Brun. 
5.	Du mottar påloggingsinfo, logger inn i [https://svarut@ks.no](https://svarut@ks.no). Klikk på Konfigurasjon.
6.	Klikk på **Organisasjonsnivået** (øverste linje i meny til venstre)
  - Faktura: innholdet i feltet «Referanse» vil bli «Deres ref» i faktura fra KS
Det er ikke nødvendig å fylle ut kundenummer.
7.	Klikk på **Avsendernivå** (ligger under organisasjonsnivået i meny til venstre)
   - Avsendernavn: Noter 
   - Servicepassord: Generer og noter. Dette er passord nr 1 og gjelder SvarUt.
  
      Avsendernavn og Servicepassord er brukernavn og passord, som du benytter når du konfigurerer lokalt integrasjonspunkt.

8.	Klikk på **Mottakersystem** (i menyen på toppen, deretter på organisasjonen din i meny på venstre side)  
   - Generer passord nr 2, dette gjeld SvarInn (ref fullstendig dokumentasjon for detaljer)
   - Last opp offentlig nøkkel til virksomhetssertifikat
  
9.	Konfigurer lokalt integrasjonspunkt, ved å legge inn brukernavn og passord fra 7. og 8. 

    Meld til Difi at dere har konfigurert lokalt integrasjonspunkt for DPF, be om tilgang til tjenesten. Difi gir tilgang.

10.	**Administrasjon** 
  - Avslutt med å følge opp det som er omtalt under kapittel Administrasjon. 
  
    Dette gjøres etter at Difi har åpnet tilgang til tjenesten. 

11.	**Verifiser at tjenesten fungerer**

Vi har god erfaring med å gjøre dette ved å utveksle meldinger med parter man har tett dialog med. 
Etter verifisering, meld til <a href="idporten@difi.no">idporten@difi.no</a> at tjenesten DPF i eFormidling fungerer tilfredsstillende.


 [**Se full veiledning her**](https://difi.github.io/move-integrasjonspunkt/ksfiks.html)

### Øke loggnivået ved behov

Det er mulig å øke loggnivået på integrasjonspunktet. Dette gjøres hovedsaklig kun under feilsøking og vil føre til mye ekstra loggmeldinger. Legg inn følgende i "integrasjonspunkt-local.properties" filen.

```
logging.level.org.springframework.ws.client.MessageTracing=TRACE
logging.level.org.springframework.ws.server.MessageTracing=TRACE
```

--- 
