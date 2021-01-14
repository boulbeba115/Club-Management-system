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
@Table(name="reunion_inviter")
@NamedQuery(name="ReunionInviter.findAll", query="SELECT ri FROM ReunionInviter ri")
public class ReunionInviter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
    @JsonBackReference(value="reunion")
    @ManyToOne
    @JoinColumn(name = "reunion_id")
    Reunion reunion;
    
    @ManyToOne
    @JoinColumn(name = "inviter_id")
    Inviter inviter;

    private Boolean absent;

	public ReunionInviter() {
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

	public Inviter getInviter() {
		return inviter;
	}

	public void setInviter(Inviter inviter) {
		this.inviter = inviter;
	}

	public Boolean getAbsent() {
		return absent;
	}

	public void setAbsent(Boolean absent) {
		this.absent = absent;
	}


    
    
}
