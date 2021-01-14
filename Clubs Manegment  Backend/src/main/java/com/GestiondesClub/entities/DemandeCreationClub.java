package com.GestiondesClub.entities;

import java.util.List;
import java.util.Date;

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

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
@Table(name="demande_creation_club")
@NamedQuery(name="demande_creation_club.findAll", query="SELECT dcc FROM DemandeCreationClub dcc")
public class DemandeCreationClub {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", updatable = false, nullable = false)
		private Long id;
		
		@Column(name="nomclub", unique = true, nullable = false)
		private String nomClub;
		
		@Temporal(TemporalType.DATE)
		@Column(name="dateCreation")
		private Date datedemande ;
		
		@Column(name = "description")
		@Type(type="text")
		private String description ;
		
		@Column(name="confirmer")
		private Boolean confirmer ;
		
		@ManyToOne
		private TypeClub type;
		
	    @OneToMany(mappedBy = "demandeCreationClub")
	    private List<MembreFondateur> membresFondateur;

	    
		public DemandeCreationClub() {
			super();
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


		public Date getDatedemande() {
			return datedemande;
		}


		public void setDatedemande(Date datedemande) {
			this.datedemande = datedemande;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public Boolean getConfirmer() {
			return confirmer;
		}


		public void setConfirmer(Boolean confirmer) {
			this.confirmer = confirmer;
		}


		public TypeClub getType() {
			return type;
		}


		public void setType(TypeClub type) {
			this.type = type;
		}


		public List<MembreFondateur> getMembresFondateur() {
			return membresFondateur;
		}


		public void setMembresFondateur(List<MembreFondateur> membresFondateur) {
			this.membresFondateur = membresFondateur;
		}



		




 
		
		
		
		
		
		
		
		
		

}
