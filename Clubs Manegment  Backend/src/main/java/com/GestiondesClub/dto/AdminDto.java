package com.GestiondesClub.dto;

import java.util.Date;

import com.GestiondesClub.entities.Niveau;

public interface AdminDto {

	Long getId();
	Long getMatricule();
	String getNom();
	String getPrenom();
	String getEmail();
	String getTel();
	String getUtilisateurAvatar();
	String getRole();
	
}
