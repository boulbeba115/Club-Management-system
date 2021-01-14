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
@Table(name="Ligne_devi")
@NamedQuery(name="LigneDevi.findAll", query="SELECT ld FROM LigneDevi ld")

public class LigneDevi {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn
	MaterielExterne matExterne;
    
    @JsonBackReference(value="les-demande-Finance")
    @ManyToOne
    @JoinColumn
    DemandeFinancement demandeFinance;
    
    private int quantite;
    
    private float montant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MaterielExterne getMatExterne() {
		return matExterne;
	}

	public void setMatExterne(MaterielExterne matExterne) {
		this.matExterne = matExterne;
	}

	public DemandeFinancement getDemandeFinance() {
		return demandeFinance;
	}

	public void setDemandeFinance(DemandeFinancement demandeFinance) {
		this.demandeFinance = demandeFinance;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}
    
    
    

}
