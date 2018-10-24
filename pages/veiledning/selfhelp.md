---
title: Selvhjelp og nyttig informasjon
description: Selvhjelp og nyttig informasjon som øking av loggnivå, loggrullering, trafikkflyt mm. 
summary: "Selvhjelp og nyttig informasjon om eFormidling"
permalink: selfhelp.html
sidebar: veiledning_sidebar
folder: veiledning
---


## Eformidling på 2 minutter - dette må du vite

- DPV = Digital post til virksomheter. Eget brukernamn/passord. Brukernamn/passord opprettes av Altinn og passord mottas på SMS. 
- DPF = . Brukernamn/passord for både svarut og svarinn (ulike). Begge settene med brukernamn/passord genereres/hentes på https://svarut.ks.no/
- DPO = Digital post til offentlige virksomheter. Eget brukernamn/passord. Opprettes selv [] 
- Ulikt brukernamn/passord for 
- Må ha virksomhetssertifikat -lenke
- Integrasjonspunktet både sender og mottar meldinger.

For informasjon om ikke tekniske ting [se Samarbeidsportalen](https://samarbeid.difi.no/felleslosninger/eformidling/ta-i-bruk-eformidling/1-forberedelser)

## Øke loggnivået ved behov

Det er mulig å øke loggnivået på integrasjonspunktet. Dette gjøres hovedsaklig kun under feilsøking og vil føre til mye ekstra loggmeldinger. Legg inn følgende i "integrasjonspunkt-local.properties" filen.

```
logging.level.org.springframework.ws.client.MessageTracing=TRACE
logging.level.org.springframework.ws.server.MessageTracing=TRACE
```

## Loggrullering


## Flyt

## Typiske feil og hvordan løse de