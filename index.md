---
title: Installasjonsveiledning for integrasjonpunktet
keywords: sample homepage
tags: [integrasjonspunkt]
sidebar: veiledning_sidebar
permalink: index.html
summary: These brief instructions will help you get started quickly with the theme. The other topics in this help provide additional information and detail about working with other aspects of this theme and Jekyll.
redirect_from: "/vStaging"

---

## [Siste versjon av integrasjonspunktet kan lastes ned her (1.7.94)](https://beta-meldingsutveksling.difi.no/service/local/repositories/releases/content/no/difi/meldingsutveksling/integrasjonspunkt/1.7.94/integrasjonspunkt-1.7.94.jar) 

### Endringslogg

[Endringslogg finner du her](https://difi.github.io/move-integrasjonspunkt/releasenotes.html#oppdatering-av-innholdet-i-veiledningen)

### Bakgrunn 

Integrasjonspunktet er per definisjon grenselinjen mellom det interne og eksterne miljøet. Intensjonen er at denne grenselinjen skal være så enkel som mulig. Erfaring viser at det kan være aktuelt å eksponere enkelte tjenester gjennom integrasjonspunktet. Dette vil være tjenester som i dag medfører kompleksitet for hver enkel leverandør, men der vi alle kan ha stor nytte av at det er en felles løsning. Eksempel på dette kan være tjenester for å bygge en sikker melding (lage ASIC-kontainer) og lokal eksponering av nasjonale tjenester relatert til adressering. En mulig tanke er at man legger alle tjenester som krever virksomhetssertifikat inn i integrasjonspunktet. Hvilke applikasjonstjenester trengs for avsender, og hvor bør de realiseres? Ved mottak av en melding, er det behov for en tilsvarende fordeling av ansvar.

Hvem validerer at meldingen er korrekt?
Hvordan skal denne gjøres tilgjengelig for de løsninger som skal håndtere meldingen lokalt?
Skal vi legge opp til en push eller pull arkitektur, eller begge deler?
Skal vi ha forskjellige strategier for å håndtere meldinger basert på størrelse? (strømmende vs pull baserte grensesnitt).

## Komme igang

For å ta ibruk integrasjonspunktet må du gjennomføre listen under

1. Installere Java 8 med JCE
2. Ansaffe virksomhetssertifikat for test og produksjon
3. Skru på ekspederingskanal for BestEdu i Sak-/Arkivsystemet (Gjelder ikke for eInnsyn)
4. Gjøre lokalt oppsett for integrasjonpunktet
5. Sørg for å holde server i Sync med NTP

<!--
## Nedlasting staging

<div class="body">										
	<div class="button custom" data-button-type="0" data-url="{{ downloadUrl }}" role="button" tabindex="0" id="downloadurl">
		<div class="logo">
			<img alt="logo" src="//www.difi.no/modules/contrib/difi_ckeditor_widgets/plugins/difibutton/icons/difibutton.png">
		</div>
		<div class="text">
			<div class="title " id="titleField">Henter versjonsinfo...</div>
			<div class="sub-title" id="subtitle1"></div>
			<div class="sub-title" id="subtitle2"></div>
		</div>
		<div class="arrow">›</div>
	</div>
</div>

<script type="text/javascript" src="js/nexusproxyclient.js">
	$(function() {
		var proxyUrl = "http://nexusproxy.azurewebsites.net/latest?env=staging&callback=?";
		$.getJSON( proxyUrl)
			.done(function( data ) { 
			$( "#downloadurl" ).attr("data-url", data.downloadUri);
			$( "#titleField").text(data.baseVersion)
			$( "#sha1Field").text( " Sha1: "+ data.sha1);
			})
		});
</script>

-->

