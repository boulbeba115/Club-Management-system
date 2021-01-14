package com.GestiondesClub.dto;

import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.MembresDeClub;

public interface LesInscritsClub {

	Long getId();
	AnneeUniversitaire getAnneUnive();
	boolean isInscripValid();	
	boolean isInscriptionConfirmation();
	AderantClub getMembresdeClub();
}
