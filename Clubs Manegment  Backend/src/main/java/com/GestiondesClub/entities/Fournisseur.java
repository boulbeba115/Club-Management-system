package com.GestiondesClub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="fournisseur")
@NamedQuery(name="Fournisseur.findAll", query="SELECT f FROM Fournisseur f")
public class Fournisseur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="matricule_fiscale",unique = true , nullable = false)
	private String matriculeFiscale;
	
	@Column(name="raison_sociale")
	private String raisonSociale;
	
	@Column(name="email",unique = true , nullable = false)
	private String email;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="fax")
	private String fax;
	

	public Fournisseur() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatriculeFiscale() {
		return matriculeFiscale;
	}

	public void setMatriculeFiscale(String matriculeFiscale) {
		this.matriculeFiscale = matriculeFiscale;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public String toString() {
		return "Fournisseur [id=" + id + ", matriculeFiscale=" + matriculeFiscale + ", raisonSociale=" + raisonSociale
				+ ", email=" + email + ", tel=" + tel + ", fax=" + fax + "]";
	}
	
	
	
}
