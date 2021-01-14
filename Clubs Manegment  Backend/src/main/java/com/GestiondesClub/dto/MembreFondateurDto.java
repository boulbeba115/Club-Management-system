package com.GestiondesClub.dto;

import java.util.Date;

import com.GestiondesClub.entities.Niveau;

public interface MembreFondateurDto {
	Long getId();
	Long getMatricule();
	String getNom();
	String getCin();
	String getPrenom();
	Date getDateNaiss();
	String getEmail();
	String getAdresse();
	String getTel();
	Niveau getNiveau();
	String getUtilisateurAvatar();
}
