package com.GestiondesClub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Role_Etudiant")
@NamedQuery(name="RoleEtudiant.findAll", query="SELECT re FROM RoleEtudiant re")
public class RoleEtudiant {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", updatable = false, nullable = false)
		private Long id;
		
		@Column(name="role", unique = true)
		private String role;

		public RoleEtudiant() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
	
		
}
