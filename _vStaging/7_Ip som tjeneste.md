Integrasjonspunktet kan også installeres som en tjeneste på server. For å gjøre dette kan en laste ned en tredjepartsprogramvare og sette opp en egen liten config-fil.

Dokumentasjonen på programvaren du trenger ligger [på github](https://github.com/kohsuke/winsw). Du trenger to filer: .exe -filen fra dette programmet og en egen .xml-fil for å fortelle .exe -filen hvilke innstillinger som skal brukes. Dette er samme konseptet som [einnsyn-klient installasjonen er basert på](https://samarbeid.difi.no/einnsyn/utrulling/installsjonsrettleiing-klient). 

1. Last ned Winsw.exe fra [github](https://github.com/kohsuke/winsw/releases). Mer informasjon om hvilken versjon du skal velge står [her](https://github.com/kohsuke/winsw) og finn "supported .NET versions".
2. last ned konfigurasjonsfila vår for [testmiljø](../resources/ip-service-staging.xml) eller [produksjonsmiljø]../resources/ip-service-prod.xml
3. Endre navn på .exe fila og xml-filene til de navnene du ønsker. Eventuelt ip-service.exe og ip-service.xml
4. Legg begge disse filene i integrasjonspunktmappa di.
5. 
