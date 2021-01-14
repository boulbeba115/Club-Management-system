package com.GestiondesClub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="reunion")
@NamedQuery(name="Reunion.findAll", query="SELECT r FROM Reunion r")
public class Reunion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="titre")
	private String titre;
	
	@Column(name="sujet")
	private String sujet;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreation ;
	
	@Temporal(TemporalType.DATE)
	private Date dateReunion ;
	
	@Temporal(TemporalType.TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Date tempdebReunion ;
	
	@Temporal(TemporalType.TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Date tempfinReunion ;
		
	@Column(name="type")
	private boolean type;
	
	@ManyToOne
	private Salle salleReunion;
	
	@JsonBackReference
	@ManyToOne
	private Club leClub;
	
	@Column(name="salle_confirmed")
	private boolean salleConfirmed;
	
	@OneToMany(mappedBy = "reunion")
	private List<ReunionInviter> lesInviter;
	
	@OneToMany(mappedBy = "reunion")
	private List<ReunionParticipant> lesParticipantsAdmins;
	@OneToMany(mappedBy = "reunion")
	private List<ParticipantEtudiant> lesParticipantsetudiants;
	
	public Reunion() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateReunion() {
		return dateReunion;
	}

	public void setDateReunion(Date dateReunion) {
		this.dateReunion = dateReunion;
	}

	public Date getTempdebReunion() {
		return tempdebReunion;
	}

	public void setTempdebReunion(Date tempdebReunion) {
		this.tempdebReunion = tempdebReunion;
	}

	public Date getTempfinReunion() {
		return tempfinReunion;
	}

	public void setTempfinReunion(Date tempfinReunion) {
		this.tempfinReunion = tempfinReunion;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public Salle getSalleReunion() {
		return salleReunion;
	}

	public void setSalleReunion(Salle salleReunion) {
		this.salleReunion = salleReunion;
	}

	public List<ReunionInviter> getLesInviter() {
		return lesInviter;
	}

	public void setLesInviter(List<ReunionInviter> lesInviter) {
		this.lesInviter = lesInviter;
	}
	
	public boolean isSalleConfirmed() {
		return salleConfirmed;
	}

	public void setSalleConfirmed(boolean salleConfirmed) {
		this.salleConfirmed = salleConfirmed;
	}
	

	public List<ReunionParticipant> getLesParticipantsAdmins() {
		return lesParticipantsAdmins;
	}

	public void setLesParticipantsAdmins(List<ReunionParticipant> lesParticipantsAdmins) {
		this.lesParticipantsAdmins = lesParticipantsAdmins;
	}

	public List<ParticipantEtudiant> getLesParticipantsetudiants() {
		return lesParticipantsetudiants;
	}

	public void setLesParticipantsetudiants(List<ParticipantEtudiant> lesParticipantsetudiants) {
		this.lesParticipantsetudiants = lesParticipantsetudiants;
	}

	public Club getLeClub() {
		return leClub;
	}

	public void setLeClub(Club leClub) {
		this.leClub = leClub;
	}

	@Override
	public String toString() {
		return "Reunion [id=" + id + ", titre=" + titre + ", sujet=" + sujet + ", dateCreation="
				+ dateCreation + ", dateReunion=" + dateReunion + ", tempdebReunion=" + tempdebReunion
				+ ", tempfinReunion=" + tempfinReunion + ", type=" + type + ", salleReunion=" + salleReunion
				+ ", lesInviter=" + lesInviter + "]";
	}
	
	
	
	
}
