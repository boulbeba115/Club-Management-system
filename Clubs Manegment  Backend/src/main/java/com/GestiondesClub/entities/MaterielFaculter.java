package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="materiel_faculter")
@NamedQuery(name="MaterielFaculter.findAll", query="SELECT mf FROM MaterielFaculter mf")
@PrimaryKeyJoinColumn(name = "id")
public class MaterielFaculter extends Materiel {
	
	@Column(name="qteTotale")
	private int qteTotale ;

	
	@JsonBackReference(value="mat-Faculter")
    @OneToMany(mappedBy = "matFaculter")
    private List<DemandeMaterielFac> lesDemandeeventMat;
    
	public MaterielFaculter() {

	}
	
	public MaterielFaculter(int qteTotale ,int qteDisponible, String nomMateriel) {
		super.setNomMateriel(nomMateriel);
		this.qteTotale = qteTotale;
	}


	public int getQteTotale() {
		return qteTotale;
	}

	public void setQteTotale(int qteTotale) {
		this.qteTotale = qteTotale;
	}



	public List<DemandeMaterielFac> getLesDemandeeventMat() {
		return lesDemandeeventMat;
	}

	public void setLesDemandeeventMat(List<DemandeMaterielFac> lesDemandeeventMat) {
		this.lesDemandeeventMat = lesDemandeeventMat;
	}





	
	
	
}
