package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;

import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeMaterielFac;
import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.Lieu;
import com.GestiondesClub.entities.TypeEvenement;

public interface DemEventDto {

	Long getId();
	String getNomEvenement();
	String getDescriptionEvenement();
    Date getDatedemande();
    Date getDatePrevuEvent();
    Date getDateFinEvent();
    boolean isExterne();
    TypeEvenement getTypeEvent();
    List<Lieu> getLeslieu();
    List<DemmandeSalle> getLesSalles();
    List<DemandeMaterielFac> getLesMaterielAffecter();
    boolean isConfirmerEvent();
    boolean isAccepterEvent();
    Boolean getApprouvEvent();
    OnlyClubDto getLeClub();
}
