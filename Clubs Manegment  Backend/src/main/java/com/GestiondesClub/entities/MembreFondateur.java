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
@Table(name="Membre_Fondateur")
@NamedQuery(name="MembreFondateur.findAll", query="SELECT ds FROM MembreFondateur ds")
public class MembreFondateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
		@ManyToOne
	    @JoinColumn(name = "etudiant_id")
	    Etudiant etudiant;
	    
	    @JsonBackReference(value="demande-creation-club")
	    @ManyToOne
	    @JoinColumn(name = "demande_creation_club_id")
	    DemandeCreationClub demandeCreationClub;

	    @ManyToOne
	    private RoleEtudiant roleEtudiant;
	    
		public MembreFondateur() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Etudiant getEtudiant() {
			return etudiant;
		}

		public void setEtudiant(Etudiant etudiant) {
			this.etudiant = etudiant;
		}

		public DemandeCreationClub getDemandeCreationClub() {
			return demandeCreationClub;
		}

		public void setDemandeCreationClub(DemandeCreationClub demandeCreationClub) {
			this.demandeCreationClub = demandeCreationClub;
		}

		public RoleEtudiant getRoleEtudiant() {
			return roleEtudiant;
		}

		public void setRoleEtudiant(RoleEtudiant roleEtudiant) {
			this.roleEtudiant = roleEtudiant;
		}
	    

}
