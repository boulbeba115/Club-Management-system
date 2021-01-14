package com.GestiondesClub.entities;

import java.util.List;

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
@Table(name="membres_de_club")
@NamedQuery(name="MvembresDeClub.findAll", query="SELECT mc FROM MembresDeClub mc")
public class MembresDeClub {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
 	@ManyToOne
    @JoinColumn(name = "club_id")
    Club club;
    
    @JsonBackReference(value="etudiant-membre")
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    Etudiant etudiant;
	
	@Column(name="role_etudiant")
	private String roleEtudiant;

 	@OneToMany(mappedBy = "membresdeClub")
    private List<InscriptionsAnnuelle> histoInscription;
 	
 	
	public MembresDeClub() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public String getRoleEtudiant() {
		return roleEtudiant;
	}

	public void setRoleEtudiant(String roleEtudiant) {
		this.roleEtudiant = roleEtudiant;
	}

	public List<InscriptionsAnnuelle> getHistoInscription() {
		return histoInscription;
	}

	public void setHistoInscription(List<InscriptionsAnnuelle> histoInscription) {
		this.histoInscription = histoInscription;
	}

	@Override
	public String toString() {
		return "MembresDeClub [id=" + id + ", club=" + club + ", etudiant=" + etudiant + ", roleEtudiant="
				+ roleEtudiant + ", histoInscription=" + histoInscription + "]";
	}
	
	

	
	
}
