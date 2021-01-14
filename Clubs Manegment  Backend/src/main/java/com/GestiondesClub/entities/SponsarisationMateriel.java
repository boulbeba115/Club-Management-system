package com.GestiondesClub.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="sponsarisation_materiel")
@NamedQuery(name="SponsarisationMateriel.findAll", query="SELECT sm FROM SponsarisationMateriel sm")
public class SponsarisationMateriel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@JsonBackReference(value="Sponsarisation-Materiel")
	@ManyToOne
	@JoinColumn(name = "evenementsponseur_id")
	EvenementSponseur evenementSpon;
	
	
	@ManyToOne
	@JoinColumn(name = "materielexterne_id")
	MaterielExterne materielExterne;
	
	@Column(name="quantite")
	private int quantite;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EvenementSponseur getEvenementSpon() {
		return evenementSpon;
	}

	public void setEvenementSpon(EvenementSponseur evenementSpon) {
		this.evenementSpon = evenementSpon;
	}

	public MaterielExterne getMaterielExterne() {
		return materielExterne;
	}

	public void setMaterielExterne(MaterielExterne materielExterne) {
		this.materielExterne = materielExterne;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}



	@Override
	public String toString() {
		return "SponsarisationMateriel [id=" + id + ", evenementSpon=" + evenementSpon + ", materielExterne=" + materielExterne + ", quantite=" + quantite + "]";
	}
	
	
	

}
