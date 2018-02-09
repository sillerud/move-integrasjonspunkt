---
title: Brannmursåpninger
keywords: brannmur
summary: "Oversikt over nødvendige brannmursåpninger"
sidebar: veiledning_sidebar
permalink: brannmur.html
folder: veiledning
---


## Test/Staging

Sentrale tjenester(Adresseoppslag, sentral konfigurasjon mm.) 
+ beta-meldingsutveksling.difi.no -> 93.94.10.30:443, 93.94.10.45:443, 93.94.10.5:443

Id-portens autentiseringstjeneste 
+ oidc-ver2.difi.no -> 146.192.252.152:443
+ oidc-ver1.difi.no -> 146.192.252.121:443

Logging 
+ 93.94.10.18:8300
+ 93.94.10.18:5343

# Meldingsformidlere

DPO og DPV
www.altinn.no -> 79.171.86.33:443

DPE 
+ move-dpe.servicebus.windows.net -> *.cloudapp.net

DPF
test.svarut.ks.no -> 193.161.160.165:443

DPI
qaoffentlig.meldingsformidler.digipost.no -> 146.192.168.18:443, 146.192.168.19:443



## Produksjon

Sentrale tjenester(Adresseoppslag, sentral konfigurasjon mm.) 
+ meldingsutveksling.difi.no -> 93.94.10.30:443, 93.94.10.45:443, 93.94.10.5:443

Meldingsformidler eInnsyn
+ move-dpe.servicebus.windows.net -> *.cloudapp.net

Om du bruker Proxy må du bruke denne i stedet, men du treng bare en.
+ move-dpe-prod.servicebus.windows.net -> *.cloudapp.net

Id-portens autentiseringstjeneste 
+ oidc.difi.no -> 146.192.252.54:443

Logging 
+ 93.94.10.18:8400
+ 93.94.10.18:5443

# Meldingsformidlere

DPO og DPV
www.altinn.no -> 79.171.86.33:443

DPE 
+ move-dpe.servicebus.windows.net -> *.cloudapp.net

Om du bruker Proxy må du bruke denne i stedet, men du treng bare en.
+ move-dpe-prod.servicebus.windows.net -> *.cloudapp.net

DPF
svarut.ks.no -> 193.161.160.165:443

DPI
qaoffentlig.meldingsformidler.digipost.no -> 146.192.168.18:443, 146.192.168.19:443

