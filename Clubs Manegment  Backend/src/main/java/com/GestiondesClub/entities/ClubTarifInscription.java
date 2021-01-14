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
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Club_Tarif_Inscription" , uniqueConstraints=@UniqueConstraint (columnNames={"anneeUniver_id", "club_id"}))
@NamedQuery(name="ClubTarifInscription.findAll", query="SELECT cti FROM ClubTarifInscription cti")
public class ClubTarifInscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "anneeUniver_id")
	AnneeUniversitaire anneeUniver;
    
    @JsonBackReference(value="tarifation-club")
    @ManyToOne
    @JoinColumn(name = "club_id")
    Club club;
    
	@Column(name="tarifation")
	private float tarifation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AnneeUniversitaire getAnneeUniver() {
		return anneeUniver;
	}

	public void setAnneeUniver(AnneeUniversitaire anneeUniver) {
		this.anneeUniver = anneeUniver;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public float getTarifation() {
		return tarifation;
	}

	public void setTarifation(float tarifation) {
		this.tarifation = tarifation;
	}

	@Override
	public String toString() {
		return "ClubTarifInscription [id=" + id + ", anneeUniver=" + anneeUniver + ", club=" + club + ", tarifation="
				+ tarifation + "]";
	}
	
	
	
}
