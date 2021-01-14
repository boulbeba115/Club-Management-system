package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Lieu")
@NamedQuery(name="Lieu.findAll", query="SELECT l FROM Lieu l")
public class Lieu{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="site")
	private String site;
	
	@Column(name="adresse")
	private String adresse;
	
	@Column(name="codepostal")
	private int codePostal;
	
	@Column(name="telContact")
	private int tel;
	
	@Column(name="faxContact")
	private int fax;
	
	@Column(name="emailContact")
	private String emailContact;
	
	@ManyToOne
	private Ville ville;
	
	@JsonBackReference (value="les-lieu")
    @ManyToMany (mappedBy = "leslieu")
    List<DemandeEvenement> lesdemandeEvent ;
	
	public Lieu() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public List<DemandeEvenement> getLesdemandeEvent() {
		return lesdemandeEvent;
	}

	public void setLesdemandeEvent(List<DemandeEvenement> lesdemandeEvent) {
		this.lesdemandeEvent = lesdemandeEvent;
	}
	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public int getFax() {
		return fax;
	}

	public void setFax(int fax) {
		this.fax = fax;
	}

	public String getEmailContact() {
		return emailContact;
	}

	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Lieu))
			return false;
		Lieu other = (Lieu) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.equals(other.ville))
			return false;
		return true;
	}

	
	

}
