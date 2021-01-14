package com.GestiondesClub.dto;

import java.util.Date;

public interface EtudiantMebClubDao {
	Long getId();
	String getNomClub();
	Date getDateCreation();
	String getClubLogo();
	Boolean getActivityStop();
}
