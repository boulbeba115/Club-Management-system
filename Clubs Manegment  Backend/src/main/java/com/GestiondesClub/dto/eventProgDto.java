package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.entities.EvenementTarif;
import com.GestiondesClub.entities.ProgrammeEvent;

public interface eventProgDto {
	Long getId();
	List<EvenementTarif> getLesTarifs();
	List<ProgrammeEvent> getLesProgrammes();
	boolean getPublication();
	List<DemandeAffiche> getLesDemandeAffiche();
	String getVedioLink(); 

	
}
