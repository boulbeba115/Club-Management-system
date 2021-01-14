package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="administration")
@NamedQuery(name="Administration.findAll", query="SELECT ad FROM Administration ad")
@PrimaryKeyJoinColumn(name = "id")
public class Administration extends Utilisateur{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="role")
	private String role ;
	
	@JsonBackReference(value="participant-adminstration")
	@OneToMany(mappedBy = "participantAdministration")
	private List<ReunionParticipant> lesReunion;

	public Administration() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
