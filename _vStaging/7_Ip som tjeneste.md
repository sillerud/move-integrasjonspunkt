Integrasjonspunktet kan også installeres som en tjeneste på server. For å gjøre dette kan en laste ned en tredjepartsprogramvare og sette opp en egen liten config-fil.

Dokumentasjonen på programvaren du trenger ligger [på github](https://github.com/kohsuke/winsw). Du trenger to filer: .exe -filen fra dette programmet og en egen .xml-fil for å fortelle .exe -filen hvilke innstillinger som skal brukes. Dette er samme konseptet som [einnsyn-klient installasjonen er basert på](https://samarbeid.difi.no/einnsyn/utrulling/installsjonsrettleiing-klient). 

1. Last ned Winsw.exe [her](https://github.com/kohsuke/winsw/releases). Mer informasjon om hvilken versjon du skal velge står [her](https://github.com/kohsuke/winsw) og finn "supported .NET versions".
2. last ned konfigurasjonsfila vår for [testmiljø](../resources/ip-service-staging.xml) eller [produksjonsmiljø](../resources/ip-service-prod.xml
3. Endre navn på .exe fila og xml-filene til de navnene du ønsker. For eksempel ip-service.exe og ip-service.xml
4. Legg begge disse filene i integrasjonspunktmappa di.
5. Endre versjonsnummeret på integrasjonspunkt-%versjonsnr%.jar til å være lik din versjon
* For å installere tjenesten gjør du følgende:
  - åpne kommandovindu som administrator og naviger til integrasjonspunktmappa. Kjør så følgende kommando
  - ip-service.exe install
  - ip-service.exe start

I denne config-fila er det lagt inn automatisk loggrotering ved 10MB størrelse og 8 filer vil bli beholdt. Dette kan endres til ønsket størrelse ved å endre ```<sizeThreshold>```variabelen.  Om du ikke ønsker loggrotering kan du fjerne hele ```<logmode>``` fra ip-service.xml

Loggene for denne tjenesten vil i utgangspunktet bli skrevet til feks ```c:\integrasjonspunkt\integrasjonspunkt-logs``` og filen ip-service.out. 

### Reinstallasjon av tjenesten

Om du gjør endringer i versjon / ip-service.xml fil så må du reinstallere tjenesten. Det gjør du ved å åpne kommandovindu som administrator og navigere til integrasjonspunktmappa. Kjør så følgende kommandoer.

- ip-service.exe stop
- ip-service.exe uninstall
- ip-service.exe install
- ip-service.exe start

Da er tjenesten reinstallert og starter opp på nytt.


