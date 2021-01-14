package com.GestiondesClub.dto;

import com.GestiondesClub.entities.Etudiant;

public interface MembreDeClubDao {

	Long getId();
	EtudiantMembre getEtudiant();
	String getRoleEtudiant();
}
