package com.GestiondesClub.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="planification")
@NamedQuery(name="Planification.findAll", query="SELECT ps FROM Planification ps")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Planification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Basic
	@Temporal(TemporalType.DATE)
	private java.util.Date dateResSalle;
	 
	@Basic
	@Temporal(TemporalType.TIME)
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private java.util.Date tempdebRes;
	
	@Basic
	@Temporal(TemporalType.TIME)
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private java.util.Date tempFinRes;
	
	@JsonBackReference(value="Demande-Salle")
	@ManyToOne(cascade=CascadeType.ALL)
	private DemmandeSalle demandeSalle;
    
	@JsonBackReference(value="Demande-Mat")
	@ManyToOne(cascade=CascadeType.ALL)
	private DemandeMaterielFac demandeMatFac;
	
	@Column(name="etatConfirmationSalle")
	private boolean etatConfirmationSalle ;
	
	@Column(name="acceptation")
	private boolean acceptation ;
	
	@Column(name="typePlanification")
	private String typePlanification   ;
	
	@Column(name="qteDemander")
	private int qteDemander ;
	
	@Column(name="qteAffecter")
	private int qteAffecter ;
	
	
	public Planification() {
		super();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public java.util.Date getDateResSalle() {
		return dateResSalle;
	}
	public void setDateResSalle(java.util.Date dateResSalle) {
		this.dateResSalle = dateResSalle;
	}
	public java.util.Date getTempdebRes() {
		return tempdebRes;
	}
	public void setTempdebRes(java.util.Date tempdebRes) {
		this.tempdebRes = tempdebRes;
	}
	public java.util.Date getTempFinRes() {
		return tempFinRes;
	}
	public void setTempFinRes(java.util.Date tempFinRes) {
		this.tempFinRes = tempFinRes;
	}

	public boolean isEtatConfirmationSalle() {
		return etatConfirmationSalle;
	}


	public void setEtatConfirmationSalle(boolean etatConfirmationSalle) {
		this.etatConfirmationSalle = etatConfirmationSalle;
	}


	public boolean isAcceptation() {
		return acceptation;
	}


	public void setAcceptation(boolean acceptation) {
		this.acceptation = acceptation;
	}


	public String getTypePlanification() {
		return typePlanification;
	}


	public void setTypePlanification(String typePlanification) {
		this.typePlanification = typePlanification;
	}


	public int getQteDemander() {
		return qteDemander;
	}


	public void setQteDemander(int qteDemander) {
		this.qteDemander = qteDemander;
	}


	public int getQteAffecter() {
		return qteAffecter;
	}


	public void setQteAffecter(int qteAffecter) {
		this.qteAffecter = qteAffecter;
	}


	public DemmandeSalle getDemandeSalle() {
		return demandeSalle;
	}


	public void setDemandeSalle(DemmandeSalle demandeSalle) {
		this.demandeSalle = demandeSalle;
	}
	
	

	public DemandeMaterielFac getDemandeMatFac() {
		return demandeMatFac;
	}


	public void setDemandeMatFac(DemandeMaterielFac demandeMatFac) {
		this.demandeMatFac = demandeMatFac;
	}


	@Override
	public String toString() {
		return "Planification [id=" + id + ", dateResSalle=" + dateResSalle + ", tempdebRes=" + tempdebRes
				+ ", tempFinRes=" + tempFinRes + "etatConfirmationSalle="
				+ etatConfirmationSalle + ", typePlanification=" + typePlanification + ", qteDemander=" + qteDemander
				+ ", qteAffecter=" + qteAffecter + "]";
	}
	
	




	
	
	

}
