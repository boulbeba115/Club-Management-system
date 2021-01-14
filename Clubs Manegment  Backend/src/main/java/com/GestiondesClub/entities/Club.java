package com.GestiondesClub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="club")
@NamedQuery(name="Club.findAll", query="SELECT c FROM Club c")
public class Club {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="nomclub", unique = true, nullable = false)
	private String nomClub;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateCreation")
	private Date dateCreation;
	
	@Column(name="clubLogo")
	private String clubLogo;
	
	@Column(name="couverture_club")
	private String couvertureClub;	
	
	@Type(type="text")
	private String termsofUse;
	
	@Column(name="activityStop")
	private Boolean activityStop = true;
	
	@Column(name="inscription_on")
	private Boolean inscriptionOn = true;
	
	@OneToMany(mappedBy = "club")
    private List<ClubTarifInscription> tarifInscription;
	
    @OneToMany
    private List<DemandeEvenement> lesDemandeDeClub;
	
    @JsonBackReference(value="leclub-etudiant")
    @OneToMany(mappedBy = "club")
    private List<MembresDeClub> lesEtudiant;
	
    @OneToOne
    private DemandeCreationClub demandeCreationClub;
	
    
	public Club() {
		super();
	}



	public Club(Long id, String nomClub, Date dateCreation, String clubLogo, Boolean activityStop,
			Boolean inscriptionOn, String couvertureClub, String termsofUse) {
		super();
		this.id = id;
		this.nomClub = nomClub;
		this.dateCreation = dateCreation;
		this.clubLogo = clubLogo;
		this.couvertureClub = couvertureClub;
		this.termsofUse = termsofUse;
		this.activityStop = activityStop;
		this.inscriptionOn = inscriptionOn;	
		}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomClub() {
		return nomClub;
	}

	public void setNomClub(String nomClub) {
		this.nomClub = nomClub;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getClubLogo() {
		return clubLogo;
	}

	public void setClubLogo(String clubLogo) {
		this.clubLogo = clubLogo;
	}


	public Boolean getActivityStop() {
		return activityStop;
	}


	public void setActivityStop(Boolean activityStop) {
		this.activityStop = activityStop;
	}

	
	
	public Boolean getInscriptionOn() {
		return inscriptionOn;
	}


	public void setInscriptionOn(Boolean inscriptionOn) {
		this.inscriptionOn = inscriptionOn;
	}


	public List<ClubTarifInscription> getTarifInscription() {
		return tarifInscription;
	}


	public void setTarifInscription(List<ClubTarifInscription> tarifInscription) {
		this.tarifInscription = tarifInscription;
	}

	
	public String getCouvertureClub() {
		return couvertureClub;
	}


	public void setCouvertureClub(String couvertureClub) {
		this.couvertureClub = couvertureClub;
	}

	public String getTermsofUse() {
		return termsofUse;
	}


	public void setTermsofUse(String termsofUse) {
		this.termsofUse = termsofUse;
	}


	public List<MembresDeClub> getLesEtudiant() {
		return lesEtudiant;
	}


	public void setLesEtudiant(List<MembresDeClub> lesEtudiant) {
		this.lesEtudiant = lesEtudiant;
	}

	
	public List<DemandeEvenement> getLesDemandeDeClub() {
		return lesDemandeDeClub;
	}


	public void setLesDemandeDeClub(List<DemandeEvenement> lesDemandeDeClub) {
		this.lesDemandeDeClub = lesDemandeDeClub;
	}

	
	public DemandeCreationClub getDemandeCreationClub() {
		return demandeCreationClub;
	}


	public void setDemandeCreationClub(DemandeCreationClub demandeCreationClub) {
		this.demandeCreationClub = demandeCreationClub;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Club))
			return false;
		Club other = (Club) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Club [id=" + id + ", nomClub=" + nomClub + ", dateCreation=" + dateCreation + ", clubLogo=" + clubLogo
				+ "]";
	}
	
	
	



}
