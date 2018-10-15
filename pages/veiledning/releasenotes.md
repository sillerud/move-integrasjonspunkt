---
title: Endringslogg integrasjonspunktet
description: Endringslogg for integrasjonspunktet
sidebar: veiledning_sidebar
permalink: releasenotes.html
folder: veiledning
---

## 1.7.93
12.10.2018
```
MOVE-956 setter connect- og sockettimeout i restklienten mot servicebus. For å unngå kø på servicebus. 
```

## 1.7.92
24.09.2018

Dette er endringer fra v1.7.90 t.o.m v.1.7.92. Ingen prod-release på 90 og 91.
```
MOVE-852 endring av databasemodell for å støtte MySQL
MOVE-839 Defaultverdier for jpInnhold, orgnr i SvarInn-melding. Mulighet for å angi "fiktiv" organisasjon dersom 
det mangler; settes via. property: difi.move.fiks.inn.fallbackSenderOrgNr=123...
MOVE-859 SvarSendesTil-felt i SvarUt-melding er nå alltid basert på InfoRecord til mottaker (tidligere hentet fra
 Avsmot)
MOVE-860 Retting av IndexOutOfBounds-feil ifm. polling av kvitteringer mot FIKS
MOVE-858 Manglende xml-tag i AppReceipt fra p360
MOVE-870 Oppgradering av azure-servicebus bibliotek (brukes ifm. batch-lesing av eInnsynsmeldinger)
MOVE-869 Fjernet namespace prefix i AppReceipt xml siden WebSak sliter med å lese disse
MOVE-874 Sikkerhetsnivå for DPF-meldinger blir nå satt basert på oppslaget fra Service registry mot SvarUt. Dersom mottaker har både nivå 3 og 4 velges sistnevnte

```

## 1.7.89
21.08.2018
```
MOVE-794 - AppReceipts på egen kø
MOVE-724 - fjernet personnr som ble logget dersom det oppstod feil
MOVE-796 - Fiks for polling ved exceptions
MOVE-725 - servicebus host flyttet til properties
MOVE-328 - dumping av payload til disk når den når DLQ
MOVE-817 - UUID-validering av sender- og receiver.ref i envelope
MOVE-836 - fiks av kritisk bug som har eksistert siden 1.7.87 
MOVE-837 - feil i mapping av svarSendesTil mot FIKS 
```

## 1.7.88
```

```

## 1.7.87
```

```

## 1.7.86
```

```

## 1.7.85
```

```

## 1.7.84
```

```

## 1.7.83
```

```

## 1.7.82
```

```

## 1.7.81
```

```
