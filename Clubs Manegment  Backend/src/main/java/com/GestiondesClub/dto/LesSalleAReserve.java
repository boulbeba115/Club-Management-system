package com.GestiondesClub.dto;

import java.util.List;

import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemmandeSalle;

public interface LesSalleAReserve {
	Long  getId();
	String getNomEvenement();
	List<DemmandeSalle> getLesSalles();
	OnlyClubDto getLeClub();
	boolean isAccepterEvent();
	
	
}
