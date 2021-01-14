package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;

import com.GestiondesClub.entities.TypeClub;

public interface CreeClubDto {
	Long getId();
	String getNomClub();
	Date getDatedemande();
	String getDescription();
	Boolean getConfirmer();
	TypeClub getType();
	List<DemandeCreMembreDto> getMembresFondateur();
}
