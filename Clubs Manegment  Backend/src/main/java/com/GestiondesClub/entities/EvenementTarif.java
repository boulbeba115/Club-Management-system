package com.GestiondesClub.entities;

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
@Table(name="evenement_tarif")
@NamedQuery(name="EvenementTarif.findAll", query="SELECT evt FROM EvenementTarif evt")
public class EvenementTarif {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
 	@ManyToOne
    @JoinColumn(name = "tarifation_id")
    Tarifation tarifation;
    
    @JsonBackReference(value="evenement-tarif")
    @ManyToOne
    @JoinColumn(name = "evenement_id")
    Evenement evenement;
    
    @Column(name="montant")
	private float montant;
	
	@Column(name="nombre_place_disp")
	private float nombrePalceDisponible;
    
	public EvenementTarif() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tarifation getTarifation() {
		return tarifation;
	}

	public void setTarifation(Tarifation tarifation) {
		this.tarifation = tarifation;
	}

	public Evenement getEvenement() {
		return evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public float getNombrePalceDisponible() {
		return nombrePalceDisponible;
	}

	public void setNombrePalceDisponible(float nombrePalceDisponible) {
		this.nombrePalceDisponible = nombrePalceDisponible;
	}
    
    
    
}
