---
title: Trafikkflyt i eFormidling og eInnsyn
description: Trafikkflyt 
summary: "Trafikkflyt i eFormidling og eInnsyn"
permalink: selfhelp_traffic_flow.html
sidebar: veiledning_sidebar
folder: veiledning
---

![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/Under_construction.png)


### Digital post til virksomheter (DPV)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpv.jpg)

Utgående: Vil bli initiert i sak-arkivsystemet og sendt til integrasjonspunktet. Meldinga vil så bli levert til Altinn sin DPV tjeneste. Leverings- og lesekvittering blir levert tilbake til avsender. Meldingen kan hentes i virksomhetens innboks i Altinn. Personen som henter meldinga må ha riktige rettigheter i Altinn for å kunne lese den (feks. sak-arkiv rolle)

Innkommende: Om virksomheten din ikke har tatt i bruk eFormidling vil du få post i innboksen i Altinn.

---

### Digital post til offentlige virksomheter (DPO)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpo.jpg)

Tekst

---

### Digital post KS FIKS SvarInn/SvarUt (DPF)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpf.jpg)

Utgående: Vil bli initiert i sak-arkivsystemet og sendt til integrasjonspunktet. Meldinga blir sendt til KS FIKS sin meldingsformidler for og så bli ekspedert til mottaker sin SvarInn innboks. Leveringskvittering vil bli sendt tilbake. 

Innkommende: Meldinger som blir sendt til SvarInn, enten via SvarUt eller som beskrevet over vil først blir lagt i SvarInn innboksen før integrasjonspunktet vil forsøke å hente meldinga for å sende den til sak-arkivsystemet. Mottaker vil kvittere tilbake.

Om mottaker ønsker det er det mulighet for å skru på e-postlevering av post frå SvarInn innboksen slik at en slipper å hente den der (om en ikke kan få det levert i sak-arkivsystemet. feks ved feil.). Dette tar eFormidling seg av vha e-postkonfigurasjonen som er satt opp i integrasjonspunkt-local.properties under DPF innstillingene. Da sender den via lokal smtp-server (den du konfigurerte). Dette må spesifikt settes på i properties-filen og er satt til false som default. ```difi.move.fiks.inn.mailOnError=false```.

eFormidling støtter også både sikkerhetsnivå 3 og 4. Ved forsendelser vil integrasjonspunktet slå opp mot Service Registry (adresse/tilgangsregister) for å sjekke det høyeste sikkerhetsnivået mottaker støtter. Meldinga blir dermed sendt på høyste støttede sikkerhetsnivå.  Det er for e-postutsendelser dette er aktuelt, ved sikkerhetsnivå 3 ligger vedlegg ved i e-posten, i sikkerhetsnivå 4 blir det sendt lenke til vedlegget i stedet. 

---

### Digital post til innbygger (DPI)
![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpi.jpg)

Tekst

---
### eInnsyn (DPE)

![](https://raw.githubusercontent.com/difi/move-integrasjonspunkt/gh-pages/resources/flyt_dpe.png)

1. Arkivar henter trigger eksport av oep saker
2. Laster opp oep fil til filområde arkivar og eInnsynsklient har tilgang til
3. eInnsynsklient splitter opp oep meldingen til eInnsynsmeldinger,
4. Laster eInnsysnsmelding til integrasjonspunktet
5. Integrasjonspunktet gjør oppslag for å finne mottaker (capability oppslag)
6. Intgrasjonspunktet krypterer, signerer og pakker melding. Laster deretter opp til mottakers kø
7. Ingegrasjonspunktet laster ned nye meldinger fra kø, pakker opp, sjekker signatur, dekrypterer melding, tilgjengeligjør for mottaker
8. eInnsysnsapplikasjon henter meldinger fra integrasjonpunktet, tilgjengeliggjør i eInnsynssøk
9. Person søker innsyn
10. Innsynskrav lastes opp til integrasjonspunkt
11. Integrasjonspunktet gjør oppslag for å finne mottaker (capability oppslag)
12. Integrasjonspunktet krypterer, signerer og pakker melding. Laster deretter opp til mottakers kø
13. Integrasjonspunktet laster ned nye meldinger fra kø, pakker opp, sjekker signatur, dekrypterer melding, tilgjengeliggjør for mottaker
14. eInnsynsklient sender innsynskrav via mottakers mailserver
15. Innsynskrav tilgjengeliggjøres i via mottakers sakarkvisystem/mailserver eller lignende.

---

