package com.GestiondesClub.dto;

import com.GestiondesClub.entities.Etudiant;

public interface etudiantParticipantDto {

	Long getId();
	OnlyEtudiant getParticipantEtudiant();
	Boolean getAbsent();
}
