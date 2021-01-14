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
@Table(name="annee_universitaire")
@NamedQuery(name="AnneeUniversitaire.findAll", query="SELECT au FROM AnneeUniversitaire au")
public class AnneeUniversitaire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="annee_deb")
	private int anneeDeb;
	
	@Column(name="annee_fin")
	private int anneeFin;
	
    @JsonBackReference(value="tarifation-des-club")
	@OneToMany(mappedBy = "anneeUniver")
    private List<ClubTarifInscription> lesClub;
    
    @JsonBackReference(value="lesInscription")
 	@OneToMany(mappedBy = "anneUnive")
     private List<InscriptionsAnnuelle> anneeInscriptions;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getAnneeDeb() {
		return anneeDeb;
	}
	
	public void setAnneeDeb(int anneeDeb) {
		this.anneeDeb = anneeDeb;
	}

	public int getAnneeFin() {
		return anneeFin;
	}
	public void setAnneeFin(int anneeFin) {
		this.anneeFin = anneeFin;
	}
	public List<ClubTarifInscription> getLesClub() {
		return lesClub;
	}
	
	public void setLesClub(List<ClubTarifInscription> lesClub) {
		this.lesClub = lesClub;
	}

	public List<InscriptionsAnnuelle> getAnneeInscriptions() {
		return anneeInscriptions;
	}

	public void setAnneeInscriptions(List<InscriptionsAnnuelle> anneeInscriptions) {
		this.anneeInscriptions = anneeInscriptions;
	}

	@Override
	public String toString() {
		return "AnneeUniversitaire [id=" + id + ", anneeDeb=" + anneeDeb + ", anneeFin=" + anneeFin +"]";
	}
	
	
	
	
	
	
}
