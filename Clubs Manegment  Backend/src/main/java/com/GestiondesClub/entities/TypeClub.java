package com.GestiondesClub.entities;

import java.util.Date;

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
import org.hibernate.annotations.Type;

@Entity
@Table(name="TypeClub")
@NamedQuery(name="TypeClub.findAll", query="SELECT tc FROM TypeClub tc")
public class TypeClub {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", updatable = false, nullable = false)
		private Long id;
		
		@Column(name="type", unique = true)
		private String type;
		
		public TypeClub() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
		
		
		
		
		
		

}
