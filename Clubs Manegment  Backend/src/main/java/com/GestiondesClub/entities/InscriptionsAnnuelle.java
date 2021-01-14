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
@Table(name="inscriptions_annuelle")
@NamedQuery(name="InscriptionsAnnuelle.findAll", query="SELECT ia FROM InscriptionsAnnuelle ia")
public class InscriptionsAnnuelle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@ManyToOne
    @JoinColumn(name = "annee_unive_id")
	AnneeUniversitaire anneUnive;
    
    @JsonBackReference(value="lesmembres")
    @ManyToOne
    @JoinColumn(name = "membresdeClub_id")
    MembresDeClub membresdeClub;
    
   private boolean inscripValid ;
   private boolean inscriptionConfirmation ;
   
	public InscriptionsAnnuelle() {
	super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AnneeUniversitaire getAnneUnive() {
		return anneUnive;
	}
	public void setAnneUnive(AnneeUniversitaire anneUnive) {
		this.anneUnive = anneUnive;
	}
	public MembresDeClub getMembresdeClub() {
		return membresdeClub;
	}
	public void setMembresdeClub(MembresDeClub membresdeClub) {
		this.membresdeClub = membresdeClub;
	}
	public boolean isInscripValid() {
		return inscripValid;
	}
	public void setInscripValid(boolean inscripValid) {
		this.inscripValid = inscripValid;
	}
	public boolean isInscriptionConfirmation() {
		return inscriptionConfirmation;
	}
	public void setInscriptionConfirmation(boolean inscriptionConfirmation) {
		this.inscriptionConfirmation = inscriptionConfirmation;
	}


}
