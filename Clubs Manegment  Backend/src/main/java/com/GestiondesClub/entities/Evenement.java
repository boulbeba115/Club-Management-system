package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="evenement")
@NamedQuery(name="Evenement.findAll", query="SELECT e FROM Evenement e")
public class Evenement {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", updatable = false, nullable = false)
		private Long id;
		
		@Column(name="publication")
		private boolean publication;
		
		@OneToOne
		private DemandeEvenement demandeEvenement;
		
		@OneToMany(mappedBy = "evenement")
	    private List<EvenementTarif> lesTarifs;
		
		@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<ProgrammeEvent> lesProgrammes;
		
		@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<DemandeAffiche> lesDemandeAffiche;
		
		@OneToMany(mappedBy="evenement")
		private List<EvenementSponseur> lesSponseur;
		
		@OneToMany
	    private List<DemandeFinancement> lesDemandeFinance;
		
		@Column(name="vediolink")
		private String vedioLink;
		
		public Evenement() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public boolean isPublication() {
			return publication;
		}

		public void setPublication(boolean publication) {
			this.publication = publication;
		}
		public String getVedioLink() {
			return vedioLink;
		}

		public void setVedioLink(String vedioLink) {
			this.vedioLink = vedioLink;
		}

		public DemandeEvenement getDemandeEvenement() {
			return demandeEvenement;
		}

		public void setDemandeEvenement(DemandeEvenement demandeEvenement) {
			this.demandeEvenement = demandeEvenement;
		}
		
		public List<EvenementTarif> getLesTarifs() {
			return lesTarifs;
		}

		public void setLesTarifs(List<EvenementTarif> lesTarifs) {
			this.lesTarifs = lesTarifs;
		}
		
		public List<ProgrammeEvent> getLesProgrammes() {
			return lesProgrammes;
		}

		public void setLesProgrammes(List<ProgrammeEvent> lesProgrammes) {
			this.lesProgrammes = lesProgrammes;
		}
		
		public List<DemandeAffiche> getLesDemandeAffiche() {
			return lesDemandeAffiche;
		}

		public void setLesDemandeAffiche(List<DemandeAffiche> lesDemandeAffiche) {
			this.lesDemandeAffiche = lesDemandeAffiche;
		}
		
		public List<EvenementSponseur> getLesSponseur() {
			return lesSponseur;
		}

		public void setLesSponseur(List<EvenementSponseur> lesSponseur) {
			this.lesSponseur = lesSponseur;
		}
		
		public List<DemandeFinancement> getLesDemandeFinance() {
			return lesDemandeFinance;
		}

		public void setLesDemandeFinance(List<DemandeFinancement> lesDemandeFinance) {
			this.lesDemandeFinance = lesDemandeFinance;
		}

		@Override
		public String toString() {
			return "Evenement [id=" + id + "publication=" + publication + ", demandeEvenement=" + demandeEvenement + "]";
		}
		
		
		
		
		
}
