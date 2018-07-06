---
title: Tjenester
keywords: tjenester, dpo, dpi, dpv, dpf, dpe
tags: [dpo, dpv, dpi, dpe, dpf, dpi]
summary: "Oversikt over forkjellige tjenester du kan nå med integrasjonspunket"
sidebar: veiledning_sidebar
permalink: veiledning_sakark.html
folder: veiledning
---

Denne siden vil inneholde informasjon om konfigurasjon som må gjøres i det enkelte sakarkiv system

## ePhorte

## P360

## UTGÅENDE INNSTILLINGER

Innstillinger for utgående meldinger kan endres her:
* Logg inn på server test-sakark01 med bruker difi\sakark_inst
* Dobbeltklikk på 360SnapIn.msc som du finner på skrivebordet.
* Velg 360 Code Table Edioter på venstre menyen
* Deretter Document Dispatch Channel på høyre siden

Format: ![ChanellData](ChannelData.png)

* Trykk på «Channel Data» kolonnen i BEST/EDU raden og legg inn riktig web service URL og kryss av for Update all languages.

Format: ![SnapIn](/SnapIn.png)

* Etter endringen, kjør en iisreset via CMD

Format: ![iisreset](../iisreset.png)


## INNKOMMENDE INNSTILLINGER

For innkommende meldingen skal følgende service brukes.
http://<maksinnavn>:8088/SI.WS.Core/Integration/EDUImport.svc/EDUImportService
Importen bør utføres med bruker <domene>\svc_sakark

## WebSak


### Batch read for eInnsyn-meldinger

Fra og med versjon 1.7.82 av integrasjonspunktet er det mulig å bruke batch read når en leser meldinger fra Azure Service-bus. Dette gjøres ved å benytte Advanced Message Queuing Protocol (AMQP). [Les mer her](https://docs.microsoft.com/en-us/azure/service-bus-messaging/service-bus-performance-improvements) Dette fungerer ikke via rest-grensesnittet. 

For å aktivere Batch read i ditt integrasjonspunkt så må du ha versjon 1.7.82 eller nyere og legge inn følgende i *integrasjonspunkt-local.properties* filen
```
difi.move.nextmove.serviceBus.batchRead=true
```

I tillegg må du åpne port 5671 for utgående trafikk. 

---
