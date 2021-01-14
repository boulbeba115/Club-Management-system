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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="utilisateur")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
@Inheritance(strategy =InheritanceType.JOINED)
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="matricule", unique = true, nullable = false)
	private Long matricule;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="cin", unique = true, nullable = false)
	private String cin;
	
	@Column(name="prenom")
	private String prenom;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateNaiss")
	private Date dateNaiss;
	
	@Column(name="email", unique = true, nullable = false)
	private String email;
	
	@Column(name="lelogin", unique = true, nullable = false)
	private String lelogin;
	
	@Column(name="adresse")
	private String adresse;
	
	@Column(name="utilisateurAvatar")
	private String utilisateurAvatar;
	
	@Column(name="tel")
	private String tel;
	@Column(name="motPass")
	private String motPass;
	
	
	
	public Utilisateur() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatricule() {
		return matricule;
	}

	public void setMatricule(Long matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
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

	public String getUtilisateurAvatar() {
		return utilisateurAvatar;
	}

	public void setUtilisateurAvatar(String utilisateurAvatar) {
		this.utilisateurAvatar = utilisateurAvatar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Utilisateur))
			return false;
		Utilisateur other = (Utilisateur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	
	
	
	
}
