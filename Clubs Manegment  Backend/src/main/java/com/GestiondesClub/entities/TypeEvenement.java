package com.GestiondesClub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="TypeEvenement")
@NamedQuery(name="TypeEvenement.findAll", query="SELECT te FROM TypeEvenement te")
public class TypeEvenement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="typeEvenement", unique = true)
	private String typeEvenement;

	@Column(name="localiter")
	private int localiter;
	
	public TypeEvenement() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeEvenement() {
		return typeEvenement;
	}

	public void setTypeEvenement(String typeEvenement) {
		this.typeEvenement = typeEvenement;
	}

	public int getLocaliter() {
		return localiter;
	}

	public void setLocaliter(int localiter) {
		this.localiter = localiter;
	}


	
	
	

}
