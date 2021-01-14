package com.GestiondesClub.dto;
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

import com.GestiondesClub.entities.ClubTarifInscription;
import com.GestiondesClub.entities.DemandeCreationClub;
import com.GestiondesClub.entities.DemandeEvenement;
import com.GestiondesClub.entities.MembresDeClub;
import com.fasterxml.jackson.annotation.JsonBackReference;


public class DtoClub {
	private Long id;
	private String nomClub;
	private Date dateCreation;
	private String clubLogo;
	private String couvertureClub;	
	private String termsofUse;
	private Boolean activityStop = true;
	private Boolean inscriptionOn = true;
    private List<ClubTarifInscription> tarifInscription;
	private Boolean isMember;
   
	public DtoClub() {
		super();
	}


	public Long getId() {
		return id;
	}
	
	public DtoClub(Long id, String nomClub, Date dateCreation, String clubLogo, Boolean activityStop,
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

	
	public Boolean getIsMember() {
		return isMember;
	}


	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}


	@Override
	public String toString() {
		return "DtoClub [id=" + id + ", nomClub=" + nomClub + ", dateCreation=" + dateCreation + ", clubLogo="
				+ clubLogo + ", couvertureClub=" + couvertureClub + ", termsofUse=" + termsofUse + ", activityStop="
				+ activityStop + ", inscriptionOn=" + inscriptionOn + ", tarifInscription=" + tarifInscription
				+ ", isMember=" + isMember + "]";
	}



	
	
	



}
