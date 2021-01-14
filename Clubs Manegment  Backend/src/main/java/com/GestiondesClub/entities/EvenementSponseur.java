package com.GestiondesClub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="evenement_sponseur")
@NamedQuery(name="EvenementSponseur.findAll", query="SELECT es FROM EvenementSponseur es")
public class EvenementSponseur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@JsonBackReference(value="evenement-sponsor")
	@ManyToOne
	@JoinColumn(name = "evenement_id")
	Evenement evenement;
	
	@ManyToOne
	@JoinColumn(name = "sponseur_id")
	Sponseur sponseur;

	@Column(name="montant")
	private int montant;
	
	@Column(name="valeur_totale")
	private Double valeurTotale;
	
	@OneToMany(mappedBy="evenementSpon")
	private List<SponsarisationMateriel> lesMaterielSpon;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_creation")
	private Date dateCreation = new Date() ;
	
		
	public EvenementSponseur() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evenement getEvenement() {
		return evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	public Sponseur getSponseur() {
		return sponseur;
	}

	public void setSponseur(Sponseur sponseur) {
		this.sponseur = sponseur;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	
	
	public List<SponsarisationMateriel> getLesMaterielSpon() {
		return lesMaterielSpon;
	}

	public void setLesMaterielSpon(List<SponsarisationMateriel> lesMaterielSpon) {
		this.lesMaterielSpon = lesMaterielSpon;
	}

	public Double getValeurTotale() {
		return valeurTotale;
	}

	public void setValeurTotale(Double valeurTotale) {
		this.valeurTotale = valeurTotale;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	public String toString() {
		return "EvenementSponseur [id=" + id + ", evenement=" + evenement + ", sponseur=" + sponseur + ", montant="
				+ montant + "]";
	}
	
	
	
}
