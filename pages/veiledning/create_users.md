---
title: Opprette brukere til eFormidling
description: Informasjon om hvordan man oppretter brukere for DPF, DPO og DPV
summary: "Informasjon om hvordan man oppretter brukere for DPF, DPO og DPV"
permalink: create_users.html
sidebar: veiledning_sidebar
folder: veiledning
---

## Hva slags brukere trenger du?

DPO og DPV har hver sin egen bruker. DPF har to, både for SvarInn og SvarUt. 

### Opprette DPO-bruker (Altinn formidlingstjeneste)

(Gjelder bare for digital post til offentlige virksomheter)
Integrasjonspunktet kjører som [datasystem](https://www.altinn.no/no/Portalhjelp/Datasystemer/) mot AltInn's meldingsformidler. Integrasjonspunktet må registeres som et datasystem AltInn's portal. Informasjon om hvordan dette gjøres finnes [her](https://www.altinn.no/no/Portalhjelp/Datasystemer/Registrere-datasystem/). En person som representerer virksomheten må logge inn på Altinn for å gjøre dette.

Når du oppretter datasystemet er det viktig at det gjøres av person som kan representere virksomheten. Hvordan man representerer virksomheten kan du lese [her](https://www.altinn.no/no/Portalhjelp/Hvordan-representere-andre/).

Under opprettelse av datasystem velger du passord og får tildelt brukerid (ID), disse skal senere brukes i properties filen som beskrives lenger nede. Dette er propertyene ```difi.move.dpo.username=``` og ```difi.move.dpo.password=```.

#### Eksempel

> Du kan gi datasystemet akkurat det navnet du ønsker. Vi har valgt å kalle den "move". Kall den gjerne eformidling_dittOrganisasjonsnummer eller annet valgfritt navn.

> I nedtrekksmenyen velger du "Formidling"

> Id'en du får er brukernavnet som skal inn i integrasjonspunkt-local.properties. Passordet du velger skal også inn i denne filen. Dette gjelder DPO. 

**Registrere datasystem:**
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/altinnDatasystemRegistrer.PNG "registere datasystem i altinn")

___

**Datasystem registrert:**


![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/altinnDatasystemRegistrert.PNG "datasystem registrert")

___

Informasjon om hvordan du logger på Altinn portal finner du <a href="https://www.altinn.no/hjelp/innlogging/">https://www.altinn.no/hjelp/innlogging/</a>.

### Opprette DPF brukere (SvarInn og SvarUt)

 [Se veiledning her](https://difi.github.io/move-integrasjonspunkt/ksfiks.html)

### Opprette DPV bruker

Dette gjøres av Altinn etter at Difi sender bestilling. For at Difi skal sende bestillingen må kunden fylle ut et informasjonsskjema. Passord mottas på SMS.

[Informasjonsskjema](https://forms.office.com/Pages/ResponsePage.aspx?id=dV4PJZxZFEaXBwztYRT_xpi569dsKKZOkO1f2ClqM-VUQzlQTzNVSUdLTjVGWFpJNk1ITjBWTkNKSy4u)

[Mer info](https://samarbeid.difi.no/felleslosninger/eformidling/ta-i-bruk-eformidling/1-forberedelser)

--- 