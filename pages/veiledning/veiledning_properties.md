---
title: Konfigurasjon av integrasjonspunkt
keywords: Properties
summary: "Generelt om lokal konfigurasjon av integrasjonspunktet"
sidebar: veiledning_sidebar
permalink: veiledning_properties.html
folder: veiledning
---

### For alle installasjoner

## Generelt

| Properties                     | Eksempel                                                           | Beskrivelse                                                                        | 
| -------------------------------| -------------------------------------------------------------------|------------------------------------------------------------------------------------|
| server.port                    | 9093                                                               | Portnummer integrasjonspunktet skal kjøre på (default 9093)                        | 
| difi.move.org.number           | 123456789                                                          | Organisasjonsnummer til din organisasjon (9 siffer)                                | 


## Keystores

Java key store

| Properties                     | Eksempel                                                           | Beskrivelse                                                                        | 
| -------------------------------| -------------------------------------------------------------------|------------------------------------------------------------------------------------|
| difi.move.org.keystore.alias   | alias                                                              | Alias til virksomhetssertifikatet som brukes i integrasjonspunktet(case sensitivt) | 
| difi.move.org.keystore.password| changeit                                                           | Passord til keystore                                                               | 
| difi.move.org.keystore.path    | difi.move.org.keystore.path=file:c:/integrasjonspunkt/keystore.jks | Path til .jks fil                                                                  | 

Windwos certificate store

| Properties                     | Eksempel                                                           | Beskrivelse                                                | 
| -------------------------------| -------------------------------------------------------------------|------------------------------------------------------------|
| difi.move.org.keystore.type    | Windows-MY                                                         | Forteller Java at en skal bruke Windows Certificate Store  | 
| difi.move.org.keystore.alias   | EgendefinertN avn                                                  | Egendefinert navn                                          | 



### Meldingstype spesifikke

## DPO

Obligatorisk

Propertie                           |Eksempel                       |Beskrivelse                                                                                                   |
------------------------------------|-------------------------------|--------------------------------------------------------------------------------------------------------------|
difi.move.feature.enableDPO=true    |true                           |Skrur på muligheten til å sende og motta DPO meldinger vi integrasjonspunktet                                 |
difi.move.noarkSystem.endpointURL   |Se eksempel i properties-filen |URL integrasjonspunktet finner sak-/arkivsystemets BestEdu tjenester                                          |
difi.move.noarkSystem.type          |ephorte/P360/WebSak/mail       |Sak/-arkivsystem type                                                                                         |
difi.move.noarkSystem.username\*    |svc_sakark                     |Brukernavn for autentisering mot sak-/arkivsystem                                                             |
difi.move.noarkSystem.password\*    |changeit                       |Passord for autentisering mot sak-/arkivsystem                                                                |
difi.move.noarkSystem.domain\*      |                               |Domene sakarkivsystemet kjører på                                                                             |
difi.move.dpo.username              |                               |Brukernavnet du får ved [opprettelse av AltInn systembruker](#opprette-bruker-til-altinn-formidlingstjeneste) |
difi.move.dpo.password              |                               |Passord du satte når du opprettet AltInn systembruker                                                         |

\* Autentisering mot sakarkivsystem benyttes av P360

Settes ved behov

Propertie                           |Eksempel                       |Beskrivelse                                                                                                   |
------------------------------------|-------------------------------|--------------------------------------------------------------------------------------------------------------|
difi.move.msh.endpointURL\*         |                               |Path til MSH  |                 

\* Denne brukes bare dersom du allerede har BestEdu og ønsker å sende filer via gammel MSH til deltakere som ikke er en del av piloten. Integrasjonspunktet vil da opptre som en proxy.



## DPV

Propertie                       |Eksempel|Beskrivelse                                                                                                   |
--------------------------------|--------|--------------------------------------------------------------------------------------------------------------|
difi.move.feature.enableDPV=true|true    |Skrur på muligheten til å sende meldinger til private virksomhter via AltInn                                  |
difi.move.dpv.username          |        |Brukernavn for AltInn tjenesteeier                                                                            |
difi.move.dpv.password          |        |Passord for overnevnte bruker                                                                                 |


## DPF

Obligatoriske

Propertie                       |Eksempel      |Beskrivelse                                                                                                   |
--------------------------------|--------------|--------------------------------------------------------------------------------------------------------------|
difi.move.fiks.inn.username     |username      ||
difi.move.fiks.inn.password     |password      ||
difi.move.fiks.ut.username      |username      ||
difi.move.fiks.ut.password      |password      ||


Kan endres
Propertie                         |Eksempel      |Beskrivelse                                                                                                                  |
----------------------------------|--------------|-----------------------------------------------------------------------------------------------------------------------------|
difi.move.fiks.inn.mailOnError    |true          |Dersom import til Sak-/Arkivsystem feiler grunnet manglende verdier kan det uatomatisk sendes mail med meldingen fra SvarInn |
difi.move.fiks.inn.mailSubject    |Import feilet |Overskrive defaultmeldingen i mail                                                                                           |
                                  |              ||
difi.move.fiks.keystore.alias*    |              ||
difi.move.fiks.keystore.path*     |              ||
difi.move.fiks.keystore.password* |              ||
difi.move.fiks.keystore.type*     |              ||

\* Default brukes samme keystore oppsett som under difi.move.org.keystore

## DPI

Obligatorisk

Propertie                        |Eksempel|Beskrivelse                                                                                                   |
---------------------------------|--------|--------------------------------------------------------------------------------------------------------------|
difi.move.feature.enableDPI      |true    |Skrur på muligheten til å sende meldinger til innbygger                                                       |

Kan Endres

Propertie                        |Eksempel|Beskrivelse                                                                                                   |
---------------------------------|--------|--------------------------------------------------------------------------------------------------------------|
difi.move.dpi.keystore.alias=${difi.move.org.keystore.alias}
difi.move.dpi.keystore.path=${difi.move.org.keystore.path}
difi.move.dpi.keystore.password=${difi.move.org.keystore.password}
difi.move.dpi.keystore.type

\* Default brukes samme keystore oppsett som under difi.move.org.keystore



### Annet

## Status database

H2

Propertie                        |Eksempel                                         |Beskrivelse                      |
---------------------------------|-------------------------------------------------|---------------------------------|
spring.datasource.url            |dbc:h2:file:./integrasjonspunkt-db               |Path til databasefil             |


MySql

Propertie                        |Eksempel                                         |Beskrivelse                      |
---------------------------------|-------------------------------------------------|---------------------------------|
spring.datasource.url            |jdbc:mysql://localhost:3306/integrasjonspunkt-db |                                 |
spring.datasource.username       |bruker                                           |                                 |
spring.datasource.password       |passord                                          |                                 |

SQL Server

Propertie                        |Eksempel                                         |Beskrivelse                      |
---------------------------------|-------------------------------------------------|---------------------------------|
spring.datasource.url            |jdbc:sqlserver://localhost;databaseName=dbnavn   |                                 |
spring.datasource.username       |bruker                                           |                                 |
spring.datasource.password       |passord                                          |                                 |
spring.datasource.driverClassName|com.microsoft.sqlserver.jdbc.SQLServerDriver     |                                 |

## SSL

## Status
spring 

## Logging

