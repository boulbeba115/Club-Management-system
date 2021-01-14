package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;

import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.ParticipantEtudiant;
import com.GestiondesClub.entities.ReunionInviter;
import com.GestiondesClub.entities.ReunionParticipant;
import com.GestiondesClub.entities.Salle;

public interface ReunionDto {

	Long getId();
	String getTitre();
	String getSujet();
	Date getDateCreation();
	Date getDateReunion();
	Date getTempdebReunion();
	Date getTempfinReunion();
	boolean isType();
	boolean isSalleConfirmed();
	Salle getSalleReunion();
	List<ReunionInviter> getLesInviter();
	List<ParicipantDto> getLesParticipantsAdmins();
	List<etudiantParticipantDto> getLesParticipantsetudiants();
	OnlyClubDto getLeClub();
	
	
}
