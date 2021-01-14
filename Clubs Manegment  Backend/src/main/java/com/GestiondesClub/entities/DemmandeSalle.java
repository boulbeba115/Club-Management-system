package com.GestiondesClub.entities;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="demande_salle")
@NamedQuery(name="DemmandeSalle.findAll", query="SELECT ds FROM DemmandeSalle ds")
public class DemmandeSalle  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "salle_id")
    Salle salle;
    
    @JsonBackReference(value="demande-salle")
    @ManyToOne
    @JoinColumn(name = "demande_id")
    DemandeEvenement demEvent;
    
    @OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Planification> lesplanification;
	
	public DemmandeSalle() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public DemandeEvenement getDemEvent() {
		return demEvent;
	}

	public void setDemEvent(DemandeEvenement demEvent) {
		this.demEvent = demEvent;
	}


	public List<Planification> getLesplanification() {
		return lesplanification;
	}

	public void setLesplanification(List<Planification> lesplanification) {
		this.lesplanification = lesplanification;
	}

	@Override
	public String toString() {
		return "DemmandeSalle [id=" + id + ", salle=" + salle + "," + "]";
	}

	
	
    
	
}
