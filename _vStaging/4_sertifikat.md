---
title: Virksomhetssertifikat
pageid: virksomhetssertifikat
layout: default
description: Virksomhetssertifikat
isHome: false
---

### Om virksomhetssertifikat

**NB!** Testmiljø krever **test virksomhetssertifikat**. Produksjonsertifikat vil ikke virke i test

**NB2!** Bruk sertifikatet merket som **Autentiseringssertifikatet**

**NB3!** Sertifikatet må ligge i en Java Key Store (JKS)

**NB4!** Sertifikatet **må** være utstedt til deres organisasjonsnummer

Integrasjonspunktet bruker virksomhetssertifikat til kryptering og signering av meldinger som går mellom integrasjonpunkter.
Virksomhetssertifikat som kan benyttes leveres av [Commfides](https://www.commfides.com/e-ID/Bestill-Commfides-Virksomhetssertifikat.html) og [Buypass](http://www.buypass.no/bedrift/produkter-og-tjenester/buypass-virksomhetssertifikat)

Når du har fått sertifikatet, må det legges inn på serveren du kjører integrasjonspunket. Noter deg lokasjonen for sertifikatet, samt brukernavn og passord. 
Dette legges senere inn integrasjonspunkt-local.properties som propertiene, keystorelocation, privatekeypassword, privatekeyalias



### Konvertering til Java Key Store (JKS)

**NB!** Passord på keystore og sertifikat **MÅ** være like

**NB!** Unngå æøå i alias-navn.

Virksomhetssertifikatene **må** ligge i en Java key store. 

Konvertering av sertifikat kan gjøres via kommando i kommandovindu, eller ved bruk av gratis programvare
[keystore explorer.](http://keystore-explorer.org/downloads.html) 

**konvertere sertifikat i keystore explorer**

Dersom du har p12 sertifikat
1. Åpne sertifikatet i Keystore Explorer 
2. På arbeidslinjen på toppen av vinduet:
    - Tools
    - Change Keystore type
     - Velg: JKS.
  
**Endre passord på sertifikat eller keystore:**

Det er viktig at passordet på keystore er likt passordet på sertifikatet for at integrasjonspunktet skal fungere. Her er veiledning for å endre passord på begge to.

*Endre keystore passord*
1. Åpne opp keystoren i JKS.
2. På arbeidslinjen på toppen av vinduet:
    - Tools
    - Set KeyStore password
    - skriv inn nytt passord
  
*Endre sertifikat passord*
1. Åpne opp keystore i JKS. 
2. Høgreklikk på valgt sertifikat og velg "set password" i menyen.
3. Skriv inn nytt passord.
  

**konvertere sertifikat vha kommando kan det gjøres slik: **
Dersom du har p12 sertifikat kan dette konverteres til jks format slik:

```
keytool -importkeystore -srckeystore [MY_FILE.p12] -srcstoretype pkcs12
 -srcalias [ALIAS_SRC] -destkeystore [MY_KEYSTORE.jks]
 -deststoretype jks -deststorepass [PASSWORD_JKS] -destalias [ALIAS]
```

forklaring på bruk av kommandoen finnes [her](https://www.tbs-certificates.co.uk/FAQ/en/626.html)

Keytool finner du i

```
%JAVA_HOME%/bin
```

(f.eks C:\Program Files\Java\jre1.8.0_101\bin)

### Laste opp public virksomhetssertifikat

NB! Zip sertifikatfila før du sender den.

For at Difi skal vite hvem sitt Integrasjonspunkt det er så må sertifikatet lastes opp hos Difi. Dette gjøres ved å sende 
Public key (.cer fil) på e-post til [idporten@difi.no](mailto:idporten@difi.no). Dette er en midlertidig løsning og vil bli erstattet av en selvbehandlingstjeneste snart.

<!-- Public key (.cer fil) lastes opp til [virksomhetssertifikatserveren for test](https://beta-meldingsutveksling.difi.no/virksomhetssertifikat/) og [virksomhetssertifikatserveren for produksjon](https://meldingsutveksling.difi.no/virksomhetssertifikat/) -->

**eksportere public key fra keystore explorer**
1. Åpne opp keystore i JKS. 
2. Høgreklikk på valgt sertifikat og velg "export->public key" i menyen.
3. Naviger til valgt mappe og lagre som .cer fil.

**public key kan eksporteres fra keystore med kommandoen**

```
keytool -export -keystore [MY_KEYSTORE.jks] -alias [ALIAS] -file [FILENAME.cer]
```
