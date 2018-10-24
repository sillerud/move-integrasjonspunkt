---
title: Typiske feil og hvordan løse de
description: Selvhjelp og nyttig informasjon som øking av loggnivå, loggrullering, trafikkflyt mm. 
summary: "Selvhjelp og nyttig informasjon om eFormidling"
permalink: selfhelp_typical_errors.html
sidebar: veiledning_sidebar
folder: veiledning
---

Mykje kan gå feil når ein setter opp integrasjonspunktet. Under har vi forsøkt å liste opp vanlege feil og korleis du kan unngå eller løyse desse sjølv.

Her vil det ligge forskjellige typer feil delt opp i 4 kategorier: Generelt, DPO, DPV, DPF.
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/Under_construction.png)

## Generelle feil
- White spaces bak linjer i ```integrasjonspunkt-local.properties``` fila kan ofte føre til feil. Sørg for å fjerne desse

### 400 bad request
400 Bad request feil i loggen betyr ofte at du forsøker å bruke et scope du ikkje har tilgang til. Typisk sett fordi dette ikkje er åpna på Difi si side. Dei scopesa du forsøker å bruke er bestemt av properties som feks ```difi.move.feature.enableDPO=true``` eller ```difi.move.feature.enableDPV=true```
  
Kontakt Difi på <a href="mailto:idporten@difi.no">idporten@difi.no</a> og be om tilgang. Send gjerne med application.log 

## DPO 
Typiske feil: brukernamn/passord, manglande tilganger, feil i integrasjonspunkt-local.properties. Sørg for at brukernamnet er det som blei autogenerert når du oppretta brukaren. [Les her for meir info](https://difi.github.io/move-integrasjonspunkt/create_users.html#opprette-dpo-bruker-altinn-formidlingstjeneste)

### ErrorId 0. UserId 0
```Could not get list of available files from Altinn formidlingstjeneste. Reason: reason: null. LocalizedErrorMessage: Errorid 0. UserId 0```

Feil med brukernamn/passord. Antageligvis mangler det innhold i propertyen ```difi.move.dpo.password=``` og ```difi.move.dpo.username=```

### ErrorId 5. UserId 0
```InvalidSystemName/Id. Must be a number or valid username. Errorid 5. Userid: 0```

Typisk pga skrivefeil i brukernamn/passord. 

### ErrorId 6. UserId 0
```message Could not get list of available files from Altinn formidlingstjeneste. Reason: Reason: An exception happened when trying to authenticate the system . LocalizedErrorMessage: An exception happened when trying to authenticate the system . ErrorId: 6. UserId: 0```

Typisk pga brukernamn/passord feil. Muligens white spaces

### ErrorId 40202. UserId 0. Not in SRR with appropriate rights
```Failed to initiate Altinn broker service Reason: There was errors in the list of recipients: The following recipients is not in SRR with appropriate rights: "123456789". ErrorId 40202. UserId 0
```
Manglande SRR rettigheter hos Altinn. Kontakt Difi og oppgje orgnr og brukernamn til DPO. <a href="mailto:idporten@difi.no">idporten@difi.no</a>. Difi vil kontakte Altinn på vegne av dykk og få det retta.

### The given reportee is not authorized to send files.
```no.difi.meldingsutveksling.shipping.ws.AltinnWsException: failed to initiate Altinn broker service Reason: The given reportee is not authorized to send files..```

Dette er også ein SRR feil. Kontakt Difi og oppgje orgnr og brukernamn til DPO. <a href="mailto:idporten@difi.no">idporten@difi.no</a>. Difi vil kontakte Altinn på vegne av dykk og få det retta.

### Failed delivering to archive
Her kan det være mange forskjellige grunner. Dette er ei veldig generell feilmelding som seie at innkommande DPO-melding ikkje kunne sendast til sak-arkivsystemet. Her må ein lese nærmare på feilmeldinga for å sjå kva det er. 

Feks: ```difi.move.noarkSystem.endpointURL``` er ikkje satt eller er feil og integrasjonspunktet får ikkje koble til sak-arkivsystemet. Det kan kan feks være ei slik feilmelding:

```
Caused by: java.lang.NullPointerException: null

    at no.difi.meldingsutveksling.noarkexchange.IntegrajonspunktReceiveImpl.forwardToNoarkSystemAndSendReceipts(IntegrajonspunktReceiveImpl.java:172)

    at no.difi.meldingsutveksling.noarkexchange.IntegrajonspunktReceiveImpl.forwardToNoarkSystem(IntegrajonspunktReceiveImpl.java:148)

    at no.difi.meldingsutveksling.noarkexchange.receive.InternalQueue.sendToNoarkSystem(InternalQueue.java:317)
```
Sjekk at ...endpointURL er korrekt satt. Kontakt Difi på <a href="mailto:idporten@difi.no">idporten@difi.no</a> ved spørsmål.

## DPV

## DPF

