package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;


public interface ClubMembreDto {
	Long getId();
	String getNomClub();
	Date getDateCreation();
	String getClubLogo();
	String getCouvertureClub();
	String getTermsofUse();
	Boolean getActivityStop();
	List<MembreDeClubDao> getLesEtudiant();
	Boolean getInscriptionOn();
	
}
