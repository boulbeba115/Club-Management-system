package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.entities.EvenementSponseur;
import com.GestiondesClub.entities.EvenementTarif;
import com.GestiondesClub.entities.ProgrammeEvent;

public interface EventAllAffiche {

	Long getId();
	NotifOnly getDemandeEvenement();
	List<DemandeAffiche> getLesDemandeAffiche();
	List<ProgrammeEvent> getLesProgrammes();	
	List<EvenementSponseur> getLesSponseur();

}
