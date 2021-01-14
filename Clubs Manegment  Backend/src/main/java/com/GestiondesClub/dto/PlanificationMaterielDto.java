package com.GestiondesClub.dto;

import java.util.Date;

public interface PlanificationMaterielDto {
	Long getId();
	Date getDateResSalle();
	Date getTempdebRes();
	Date getTempFinRes();
	String getTypePlanification();
	int getQteDemander();
	int getQteAffecter();
	DemandeMatDto getDemandeMatFac();
}
