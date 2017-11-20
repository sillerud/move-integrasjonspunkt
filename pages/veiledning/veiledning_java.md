---
title: Java 
keywords: Java
sidebar: veiledning_sidebar
permalink: veiledning_java.html
folder: veiledning
---

Integrasjonpunktet er en applikasjon skrevet i programmeringsspråket Java. Java ble valgt da dette er det mest utbredete programmeringsspøråket i verden, samt at det har fordelen med at det kan kjøre på alle platformer.

For at en skal kunne kjøre en Java applikasjon må en ha en Java Virtual Machine (JVM) Installert. 

Java 8 kan lastes ned fra [denne siden](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Default støtter ikke Java krypteringsnøkler av den lengden vi bruker i integrasjonspunktet, for å oppnå god nok sikkerhet. Man må derfor laste ned og legge inn en pakke som gjør at Javainstallasjonen får utvikdet sikkerhet. 
Dersom du forsøker starte integrasjonspunktet uten JCE vil det feile fort og du vil få melding om at JCE mangler

JCE lastes ned fra [Oracels sider](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

*Vær obs på at du ved oppdateringer til nye java versjoner må legge inn JCE pakken på nytt. Kan gjennbruke pakke lastet ned tidligere
