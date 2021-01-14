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
@Table(name="reunion_participant")
@NamedQuery(name="ReunionParticipant.findAll", query="SELECT ri FROM ReunionParticipant ri")
public class ReunionParticipant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
    @JsonBackReference(value="Reunion-Participant")
    @ManyToOne
    @JoinColumn(name = "reunion_id")
    Reunion reunion;
    
    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    Administration participantAdministration;

    private Boolean absent;

	public ReunionParticipant() {
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

	public Administration getParticipantAdministration() {
		return participantAdministration;
	}

	public void setParticipantAdministration(Administration participantAdministration) {
		this.participantAdministration = participantAdministration;
	}

	public Boolean getAbsent() {
		return absent;
	}

	public void setAbsent(Boolean absent) {
		this.absent = absent;
	}


    
    
    
	
}
