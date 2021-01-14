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


@Entity
@Table(name="demande_Financement")
@NamedQuery(name="DemandeFinancement.findAll", query="SELECT d FROM DemandeFinancement d")
public class DemandeFinancement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateCreation")
	private Date dateCreation;
	
	@Column(name="le_devi")
	private String leDevi;
	
	@Column(name="etat")
	private boolean etat;

	@ManyToOne
	private Fournisseur fournisseur;
	
	@OneToMany(mappedBy = "demandeFinance")
    private List<LigneDevi> deviMateriel;
	
	
	public DemandeFinancement() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getLeDevi() {
		return leDevi;
	}

	public void setLeDevi(String leDevi) {
		this.leDevi = leDevi;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public List<LigneDevi> getDeviMateriel() {
		return deviMateriel;
	}

	public void setDeviMateriel(List<LigneDevi> deviMateriel) {
		this.deviMateriel = deviMateriel;
	}

	@Override
	public String toString() {
		return "DemandeFinancement [id=" + id + ", dateCreation=" + dateCreation + ", leDevi=" + leDevi + ", etat="
				+ etat + "]";
	}
	
	
}
