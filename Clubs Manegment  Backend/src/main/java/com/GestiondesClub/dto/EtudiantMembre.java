package com.GestiondesClub.dto;

import java.util.Date;

import com.GestiondesClub.entities.Niveau;

public interface EtudiantMembre {
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
	String getUtilisateurAvatar();
}
