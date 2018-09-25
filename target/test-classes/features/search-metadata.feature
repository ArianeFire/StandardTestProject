Feature: Recherche des metadonnees

	Scenario Outline: Recherche de metadonnee par code EC & Metadonnee
		Given J'ai le critere de recherche de metadonnees suivant:
			| nomenclature		| ecIds		| 	metadonneesCodes		| niveauIntermediaire |
			|	<nomenclature>	|	<ecIds>	| 		<metadonneesCodes>	|		true		  |
			
		When Quand j'applique ces criteres 
		Then Je dois avoir la metadonnee ayant le code <result> et nombre d'EC est <taille>
			Examples:
				| nomenclature 	| ecIds 				|  metadonneesCodes 		| result 			|	taille		|
				| CIM10V2		|  A00.9		  		| 	Code PMSI				|	Code PMSI		|		1		|
				| CIM10V2		|  A00.0,A00.9,A00 		| 	Code PMSI				|	Code PMSI		|		3		|