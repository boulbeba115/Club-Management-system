package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="materiel_externe")
@NamedQuery(name="MaterielExterne.findAll", query="SELECT me FROM MaterielExterne me")
@PrimaryKeyJoinColumn(name = "id")
public class MaterielExterne extends Materiel{
	
	
	@Column(name="coutMateriel")
	private int coutMateriel ;
	
	@JsonBackReference(value="materiel-Externe")
	@OneToMany(mappedBy="materielExterne")
	private List<SponsarisationMateriel> lesEvenementSponsoriser;

	@JsonBackReference(value="les-demandes-finance")
    @OneToMany(mappedBy = "matExterne")
    private List<LigneDevi> lesDemandFinance;
	
	public int getCoutMateriel() {
		return coutMateriel;
	}

	
	public MaterielExterne() {
		super();
	}


	public void setCoutMateriel(int coutMateriel) {
		this.coutMateriel = coutMateriel;
	}

	


	public List<SponsarisationMateriel> getLesEvenementSponsoriser() {
		return lesEvenementSponsoriser;
	}


	public void setLesEvenementSponsoriser(List<SponsarisationMateriel> lesEvenementSponsoriser) {
		this.lesEvenementSponsoriser = lesEvenementSponsoriser;
	}


	public List<LigneDevi> getLesDemandFinance() {
		return lesDemandFinance;
	}


	public void setLesDemandFinance(List<LigneDevi> lesDemandFinance) {
		this.lesDemandFinance = lesDemandFinance;
	}
	
	
	
}
