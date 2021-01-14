package com.GestiondesClub.dto;

import java.util.Date;

import com.GestiondesClub.entities.DemandeCreationClub;

public interface Clubdto {
	
		Long getId();
		String getNomClub();
		Date getDateCreation();
		String getClubLogo();
		String getCouvertureClub();
		Boolean getActivityStop();
		CreeClubDto getDemandeCreationClub();
}

