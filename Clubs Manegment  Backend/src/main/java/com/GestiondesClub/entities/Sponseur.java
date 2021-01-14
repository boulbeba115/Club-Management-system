package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="sponseur")
@NamedQuery(name="Sponseur.findAll", query="SELECT s FROM Sponseur s")
public class Sponseur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="raison_sociale",unique = true , nullable = false)
	private String raisonSociale;
	
	@Column(name="url_site_sponseur")
	private String urlSiteSponseur;
	
	@Column(name="logo_sponseur")
	private String logoSponseur = "no_image.jpg";
	
	@Column(name="active")
	private boolean active = true;
	
	@JsonBackReference(value="sponsor")
	@OneToMany(mappedBy="sponseur")
	private List<EvenementSponseur> lesEvenement;
	
	public Sponseur() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getUrlSiteSponseur() {
		return urlSiteSponseur;
	}

	public void setUrlSiteSponseur(String urlSiteSponseur) {
		this.urlSiteSponseur = urlSiteSponseur;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLogoSponseur() {
		return logoSponseur;
	}

	public void setLogoSponseur(String logoSponseur) {
		this.logoSponseur = logoSponseur;
	}

	public List<EvenementSponseur> getLesEvenement() {
		return lesEvenement;
	}

	public void setLesEvenement(List<EvenementSponseur> lesEvenement) {
		this.lesEvenement = lesEvenement;
	}

	@Override
	public String toString() {
		return "Sponseur [id=" + id + ", raisonSociale=" + raisonSociale + ", urlSiteSponseur=" + urlSiteSponseur
				+ ", logo_Sponseur=" + logoSponseur + "]";
	}
	
	

}
