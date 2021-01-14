package com.GestiondesClub.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class UtilisateurDto {
	private Long id;
	private String email;
	private String lelogin;
	private String adresse;
	private String utilisateurAvatar;
	private String tel;
	private String motPass;
	private String oldMotPass;

	public UtilisateurDto() {
		super();
	}
	public UtilisateurDto(Long id, String email, String lelogin, String adresse, String utilisateurAvatar, String tel,
			String motPass) {
		super();
		this.id = id;
		this.email = email;
		this.lelogin = lelogin;
		this.adresse = adresse;
		this.utilisateurAvatar = utilisateurAvatar;
		this.tel = tel;
		this.motPass = motPass;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLelogin() {
		return lelogin;
	}
	public void setLelogin(String lelogin) {
		this.lelogin = lelogin;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getUtilisateurAvatar() {
		return utilisateurAvatar;
	}
	public void setUtilisateurAvatar(String utilisateurAvatar) {
		this.utilisateurAvatar = utilisateurAvatar;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMotPass() {
		return motPass;
	}
	public void setMotPass(String motPass) {
		this.motPass = motPass;
	}
	public String getOldMotPass() {
		return oldMotPass;
	}
	public void setOldMotPass(String oldMotPass) {
		this.oldMotPass = oldMotPass;
	}
	
	
}
