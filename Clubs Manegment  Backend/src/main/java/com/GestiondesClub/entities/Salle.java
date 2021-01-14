package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="salle")
@NamedQuery(name="Salle.findAll", query="SELECT s FROM Salle s")
public class Salle  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="nomSalle")
	private String nomSalle ;
	
	@Column(name="capcite")
	private int capcite ;
	
	@Column(name="retroproj")
	private boolean retroproj ;
	
	@ManyToOne
	private TypeSalle salleType;
	
    @JsonBackReference(value="salle-demande")
    @OneToMany(mappedBy = "salle")
    private List<DemmandeSalle> lesDemande;
    
    @JsonBackReference(value="reunion-salle")
    @OneToMany
	private List<Reunion> lesReunion;
	
	public Salle() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomSalle() {
		return nomSalle;
	}

	public void setNomSalle(String nomSalle) {
		this.nomSalle = nomSalle;
	}

	public int getCapcite() {
		return capcite;
	}

	public void setCapcite(int capcite) {
		this.capcite = capcite;
	}

	public boolean isRetroproj() {
		return retroproj;
	}

	public void setRetroproj(boolean retroproj) {
		this.retroproj = retroproj;
	}

	public List<DemmandeSalle> getLesDemande() {
		return lesDemande;
	}

	public void setLesDemande(List<DemmandeSalle> lesDemande) {
		this.lesDemande = lesDemande;
	}

	public TypeSalle getSalleType() {
		return salleType;
	}

	public void setSalleType(TypeSalle salleType) {
		this.salleType = salleType;
	}

	@Override
	public String toString() {
		return "Salle [id=" + id + ", nomSalle=" + nomSalle + ", capcite=" + capcite + ", retroproj=" + retroproj
				+ ", salleType=" + salleType + "]";
	}	
	
	
	
}
