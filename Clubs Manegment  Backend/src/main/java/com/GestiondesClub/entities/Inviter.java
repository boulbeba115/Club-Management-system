package com.GestiondesClub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="inviter")
@Inheritance(strategy =InheritanceType.JOINED)
@NamedQuery(name="Inviter.findAll", query="SELECT i FROM Inviter i")
public class Inviter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="prenom")
	private String prenom;
	
	@Column(name="cin", unique = true, nullable = false)
	private int cin;
	
	@Column(name="email", unique = true, nullable = false)
	private String email;
	
	@Column(name="profession")
	private String profession;
	
	@Column(name="adresse")
	private String adresse;
	
	@Column(name="tel")
	private int tel;
	
    @JsonBackReference(value="inviter")
	@OneToMany(mappedBy = "inviter")
	private List<ReunionInviter> lesReunion;
	
	public Inviter() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}
	
	public List<ReunionInviter> getLesReunion() {
		return lesReunion;
	}

	public void setLesReunion(List<ReunionInviter> lesReunion) {
		this.lesReunion = lesReunion;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Override
	public String toString() {
		return "Inviter [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", email=" + email
				+ ", adresse=" + adresse + ", tel=" + tel + "]";
	}
	
	
	
}
