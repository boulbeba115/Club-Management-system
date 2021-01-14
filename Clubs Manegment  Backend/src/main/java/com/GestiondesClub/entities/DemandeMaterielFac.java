package com.GestiondesClub.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="demande_materiel_fac")
@NamedQuery(name="DemandeMaterielFac.findAll", query="SELECT dmf FROM DemandeMaterielFac dmf")
public class DemandeMaterielFac {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "matfac_id")
    MaterielFaculter matFaculter;
    
    @JsonBackReference(value="demande-matfac")
    @ManyToOne
    @JoinColumn(name = "demande_id")
    DemandeEvenement demEventMat;
    
	
    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE})

    private List<Planification> lesplanificationMateriel;

	public DemandeMaterielFac() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public MaterielFaculter getMatFaculter() {
		return matFaculter;
	}


	public void setMatFaculter(MaterielFaculter matFaculter) {
		this.matFaculter = matFaculter;
	}


	public DemandeEvenement getDemEventMat() {
		return demEventMat;
	}


	public void setDemEventMat(DemandeEvenement demEventMat) {
		this.demEventMat = demEventMat;
	}


	public List<Planification> getLesplanificationMateriel() {
		return lesplanificationMateriel;
	}


	public void setLesplanificationMateriel(List<Planification> lesplanificationMateriel) {
		this.lesplanificationMateriel = lesplanificationMateriel;
	}

    


    
    
}
