package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeMaterielFac;

public interface LesMatARes {
	Long  getId();
	String getNomEvenement();
	List<DemandeMaterielFac> getLesMaterielAffecter();
	OnlyClubDto getLeClub();
	boolean isAccepterEvent();
}
