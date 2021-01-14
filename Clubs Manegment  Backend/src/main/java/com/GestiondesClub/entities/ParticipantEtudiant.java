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
@Table(name="participant_etudiant")
@NamedQuery(name="ParticipantEtudiant.findAll", query="SELECT pe FROM ParticipantEtudiant pe")
public class ParticipantEtudiant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
    @JsonBackReference(value="Reunion-Participant-etudiant")
    @ManyToOne
    @JoinColumn(name = "reunion_id")
    Reunion reunion;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    Etudiant participantEtudiant;

    private Boolean absent;

	public ParticipantEtudiant() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}

	public Etudiant getParticipantEtudiant() {
		return participantEtudiant;
	}

	public void setParticipantEtudiant(Etudiant participantEtudiant) {
		this.participantEtudiant = participantEtudiant;
	}

	public Boolean getAbsent() {
		return absent;
	}

	public void setAbsent(Boolean absent) {
		this.absent = absent;
	}


    
}
