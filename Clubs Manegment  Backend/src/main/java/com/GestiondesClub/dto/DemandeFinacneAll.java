package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.DemandeFinancement;


public interface DemandeFinacneAll {
	Long getId();
	NotifOnly getDemandeEvenement();
	List<DemandeFinancement> getLesDemandeFinance() ;

}
