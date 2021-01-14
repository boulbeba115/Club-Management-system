package com.GestiondesClub.dto;

import java.util.Date;
import java.util.List;

import com.GestiondesClub.entities.MembresDeClub;
import com.GestiondesClub.entities.Niveau;

public interface EtudiantClubDao {
	Long getId();
	Long getMatricule();
	String getNom();
	String getPrenom();
	String getCin();
	Date getDateNaiss();
	String getEmail();
	String getAdresse();
	String getTel();
	Niveau getNiveau();
	String getLelogin();
	String getUtilisateurAvatar();
	List<EtudiantMembreDeClubDao> getLesclub(); 	
}
