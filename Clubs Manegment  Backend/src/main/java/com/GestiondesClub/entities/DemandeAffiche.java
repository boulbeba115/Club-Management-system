package com.GestiondesClub.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="demande_affiche")
@NamedQuery(name="DemandeAffiche.findAll", query="SELECT da FROM DemandeAffiche da")
public class DemandeAffiche {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Type(type="text")
	private String DemandeEcrite ;
	
	private String DemandeImage ;
	
	private boolean confirmation;
	
	private boolean etat; 
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateCreation")
	private Date dateCreation = new Date();
	
	
	public DemandeAffiche() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDemandeEcrite() {
		return DemandeEcrite;
	}

	public void setDemandeEcrite(String demandeEcrite) {
		DemandeEcrite = demandeEcrite;
	}

	public String getDemandeImage() {
		return DemandeImage;
	}

	public void setDemandeImage(String demandeImage) {
		DemandeImage = demandeImage;
	}

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
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
		if (!(obj instanceof DemandeAffiche))
			return false;
		DemandeAffiche other = (DemandeAffiche) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DemandeAffiche [id=" + id + ", DemandeEcrite=" + DemandeEcrite + ", DemandeImage=" + DemandeImage
				+ ", confirmation=" + confirmation + ", etat=" + etat + ", dateCreation=" + dateCreation + "]";
	}

	
	
	
	
}
