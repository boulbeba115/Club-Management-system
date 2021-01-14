package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.InscriptionsAnnuelle;

public interface lesClubDeEtudiant {
	Long getId();
	EtudiantMembre getEtudiant();
	String getRoleEtudiant();
	OnlyClubDto getClub();
	List<InscriptionsAnnuelle> getHistoInscription();
}
