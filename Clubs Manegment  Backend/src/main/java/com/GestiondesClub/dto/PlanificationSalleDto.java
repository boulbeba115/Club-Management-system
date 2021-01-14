package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;

import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.TypeSalle;

public interface PlanificationSalleDto {

	Long getId();
	Date getDateResSalle();
	Date getTempdebRes();
	Date getTempFinRes();
	boolean isAcceptation();
	String getTypePlanification();
	DemandeSalleDto getDemandeSalle();
}
