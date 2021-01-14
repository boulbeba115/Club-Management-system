package com.GestiondesClub.entities;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="tarifation")
@NamedQuery(name="Tarifation.findAll", query="SELECT t FROM Tarifation t")
public class Tarifation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="tarif", unique = true, nullable = false)
	private String tarif;
	
	@JsonBackReference(value="tarifation")
	@OneToMany(mappedBy = "tarifation")
    private List<EvenementTarif> lesEvenement;
	
	public Tarifation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTarif() {
		return tarif;
	}

	public void setTarif(String tarif) {
		this.tarif = tarif;
	}
	
	
	
}
