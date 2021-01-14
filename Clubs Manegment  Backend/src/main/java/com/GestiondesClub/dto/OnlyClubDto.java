package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;

import com.GestiondesClub.entities.ClubTarifInscription;

public interface OnlyClubDto {
	Long getId();
	String getNomClub();
	Date getDateCreation();
	String getClubLogo();
	Boolean getActivityStop();
	Boolean getInscriptionOn();
	String getCouvertureClub();
	String getTermsofUse();
	List<ClubTarifInscription> getTarifInscription();
	
}
