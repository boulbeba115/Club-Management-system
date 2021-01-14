package com.GestiondesClub.dto;

import com.GestiondesClub.entities.AnneeUniversitaire;

public interface InscriptionsClubs {
	Long getId();
	AnneeUniversitaire getAnneUnive();
	boolean isInscripValid();	
	boolean isInscriptionConfirmation();
	ClubsInscriptions getMembresdeClub();
}
