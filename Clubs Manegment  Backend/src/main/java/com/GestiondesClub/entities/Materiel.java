package com.GestiondesClub.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Materiel")
@Inheritance(strategy =InheritanceType.JOINED)
@NamedQuery(name="Materiel.findAll", query="SELECT m FROM Materiel m")
public abstract class Materiel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="nomMateriel", nullable = false)
	private String nomMateriel ;
	
	@Column(name="marque" )
	private String marqueMateriel ;
	
	@Column(name="reference_materiel",unique=true , nullable = false)
	private String ReferenceMateriel ;
	
	public Materiel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomMateriel() {
		return nomMateriel;
	}

	public void setNomMateriel(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}

	public String getMarqueMateriel() {
		return marqueMateriel;
	}

	public void setMarqueMateriel(String marqueMateriel) {
		this.marqueMateriel = marqueMateriel;
	}

	public String getReferenceMateriel() {
		return ReferenceMateriel;
	}

	public void setReferenceMateriel(String referenceMateriel) {
		ReferenceMateriel = referenceMateriel;
	}
	
	
	
	
	
	

}
