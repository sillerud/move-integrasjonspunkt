---
title: Virksomhetssertifikat
keywords: virksomhetssertifikat
summary: "Generelt om installasjon av integrasjonspunktet"
sidebar: veiledning_sidebar
permalink: veiledning_virksomhetssertifikat.html
folder: veiledning
---

Integrasjonspunktet bruker virksomhetssertifikat til kryptering og signering av meldinger som går mellom integrasjonpunkter.
Virksomhetssertifikat som kan benyttes leveres av [Commfides](https://www.commfides.com/e-ID/Bestill-Commfides-Virksomhetssertifikat.html) og [Buypass](http://www.buypass.no/bedrift/produkter-og-tjenester/buypass-virksomhetssertifikat)

Når du har fått sertifikatet, må det legges inn på serveren du kjører integrasjonspunket. Noter deg lokasjonen for sertifikatet, samt brukernavn og passord. 
Dette legges senere inn integrasjonspunkt-local.properties som propertiene, keystorelocation, privatekeypassword, privatekeyalias

> * Testmiljø krever **test virksomhetssertifikat**.
> * I produksjon **må** en ha produksjon **virksomhetssertifikat**. 
> * Bruk sertifikatet merket som **Autentiseringssertifikatet**  
> * Default bruker integrasjonspunktet Java Key Store (JKS) denne veildeningen er i forhold til det, for oppsett av andre keystores, se veiledning for properties
> * Sertifikatet **må** være utstedt til deres organisasjonsnummer
> * Sertifikatet kan ikke være et wildcard sertifikat.


Før du tar i bruk virksomhetssertifikatene er det behov for å gjøre noen handlinger med de. Disse kan gjøres med verktøyene nevnt nedenfor.


## Keytore explorer

KeyStore Explorer er en åpen kildekode-GUI-erstatning for Java-kommandolinjeverktøyene Keytool og Jarsigner. KeyStore Explorer presenterer sin funksjonalitet, og mer, via et intuitivt grafisk brukergrensesnitt.

[Nedlastning](http://keystore-explorer.org/downloads.html) 

## Keytool

keytool er et nøkkel- og sertifikatstyringsverktøy. Det tillater brukere å administrere sine egne offentlige / private nøkkelpar og tilhørende sertifikater. Keytool er et komandolinjeverktøy og kommer som en del av Javainstallasjonen 

Keytool finner du i

```
%JAVA_HOME%/bin
```

(f.eks C:\Program Files\Java\jre1.8.0_101\bin)


## Konvertering til Java Key Store (JKS)

Java Key Store er default keystore brukt i integrasjonspunktet. Se properties siden for andre supporterte keystores og hvordan ta ibruk dette

**NB!** Passord på keystore og sertifikat **MÅ** være like
**NB!** Unngå æøå i alias-navn.


**Keystore explorer**

Dersom du har p12 sertifikat
1. Åpne sertifikatet i Keystore Explorer 
2. På arbeidslinjen på toppen av vinduet:
    - Tools
    - Change Keystore type
     - Velg: JKS.
     - Fil -> Lagre som -> velg namn og lagre som jks. feks "keystore.jks"
  
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
  



**Keytool**
Dersom du har p12 sertifikat kan dette konverteres til jks format slik:

```
keytool -importkeystore -srckeystore [MY_FILE.p12] -srcstoretype pkcs12
 -srcalias [ALIAS_SRC] -destkeystore [MY_KEYSTORE.jks]
 -deststoretype jks -deststorepass [PASSWORD_JKS] -destalias [ALIAS]
```

forklaring på bruk av kommandoen finnes [her](https://www.tbs-certificates.co.uk/FAQ/en/626.html)



### Laste opp public virksomhetssertifikat

NB! Zip sertifikatfila før du sender den.

For at Difi skal vite hvem sitt Integrasjonspunkt det er så må sertifikatet lastes opp hos Difi. Dette gjøres ved å sende 
Public key (.cer fil) på e-post til [idporten@difi.no](mailto:idporten@difi.no). Dette er en midlertidig løsning og vil bli erstattet av en selvbehandlingstjeneste snart.

<!-- Public key (.cer fil) lastes opp til [virksomhetssertifikatserveren for test](https://beta-meldingsutveksling.difi.no/virksomhetssertifikat/) og [virksomhetssertifikatserveren for produksjon](https://meldingsutveksling.difi.no/virksomhetssertifikat/) -->

## Eksportere public key

**Keystore explorer**
1. Åpne opp JKS-keystoren i keystore explorer. 
2. Høgreklikk på valgt sertifikat og velg "export->Certificate" eller "certificate chain" i menyen.
    - Om du velger Certificate Chain så må du markere for "head only" i det neste vinduet.
    - Marker også av for export format "X.509"
3. Marker for PEM format.
4. Naviger til valgt mappe og lagre som .cer fil.

**Keytool**

```
keytool -export -keystore [MY_KEYSTORE.jks] -alias [ALIAS] -file [FILENAME.cer]
```