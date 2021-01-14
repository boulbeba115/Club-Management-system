package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.entities.DemandeFinancement;
import com.GestiondesClub.entities.EvenementSponseur;
import com.GestiondesClub.entities.EvenementTarif;
import com.GestiondesClub.entities.ProgrammeEvent;

public interface Evenementdto {

	Long getId();
	String getVedioLink(); 
	List<EvenementTarif> getLesTarifs();
	List<ProgrammeEvent> getLesProgrammes();
	boolean getPublication();
	DemEventDto getDemandeEvenement();
	List<DemandeAffiche> getLesDemandeAffiche();
	List<EvenementSponseur> getLesSponseur();
	List<DemandeFinancement> getLesDemandeFinance() ;

	
}
