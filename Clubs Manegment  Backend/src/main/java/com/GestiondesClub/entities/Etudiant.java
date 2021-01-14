package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="etudiant")
@NamedQuery(name="Etudiant.findAll", query="SELECT e FROM Etudiant e")
@PrimaryKeyJoinColumn(name = "id")

public class Etudiant extends Utilisateur{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne
	private Niveau niveau;
	
    @OneToMany(mappedBy = "etudiant")
    private List<MembresDeClub> lesclub;
    
    @JsonBackReference(value="etudiant-founder")
    @OneToMany(mappedBy = "etudiant")
    private List<MembreFondateur> fondateurde;
    
    @JsonBackReference(value="etudiant-Membre")
	@OneToMany(mappedBy = "etudiant")
	private List<MembresDeClub> lesClub;
	
    @JsonBackReference(value="participant-adminstration")
	@OneToMany(mappedBy = "participantEtudiant")
	private List<ParticipantEtudiant> lesReunion;
    
	public Etudiant() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public List<MembresDeClub> getLesclub() {
		return lesclub;
	}

	public void setLesclub(List<MembresDeClub> lesclub) {
		this.lesclub = lesclub;
	}

	public List<MembreFondateur> getFondateurde() {
		return fondateurde;
	}

	public void setFondateurde(List<MembreFondateur> fondateurde) {
		this.fondateurde = fondateurde;
	}

	public List<ParticipantEtudiant> getLesReunion() {
		return lesReunion;
	}

	public void setLesReunion(List<ParticipantEtudiant> lesReunion) {
		this.lesReunion = lesReunion;
	}

	public List<MembresDeClub> getLesClub() {
		return lesClub;
	}

	public void setLesClub(List<MembresDeClub> lesClub) {
		this.lesClub = lesClub;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Etudiant))
			return false;
		Etudiant other = (Etudiant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	

	



}
