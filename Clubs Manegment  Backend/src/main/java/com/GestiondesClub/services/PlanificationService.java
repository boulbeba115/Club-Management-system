package com.GestiondesClub.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.PlanificationRepsitory;
import com.GestiondesClub.dto.PlanificationMaterielDto;
import com.GestiondesClub.dto.PlanificationSalleDto;
import com.GestiondesClub.entities.DemandeEvenement;
import com.GestiondesClub.entities.DemandeMaterielFac;
import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.MaterielFaculter;
import com.GestiondesClub.entities.Planification;

@Service
public class PlanificationService {
	
	@Autowired
	private PlanificationRepsitory PlanificationRepo;

	public void addplanifications(Planification p , DemmandeSalle d) {
			Planification pl = p ;
			pl.setDemandeSalle(d);
	}
	public void addplanificationsMat(Planification p, DemandeMaterielFac d) {
			Planification pl = p ;
			pl.setDemandeMatFac(d);
	}
	public Planification affecterQteMateriel(Planification planif) {
		return PlanificationRepo.save(planif);
	}

	public List<PlanificationSalleDto> findplanif(Date date) {
		return PlanificationRepo.findByDateResSalleAndAcceptationTrue(date);
	}

	public List<PlanificationMaterielDto> findPlanifMat(Date date) {
		return PlanificationRepo.findLeByDateResSalleAndTypePlanification(date,"materiel");

	}
	public List<Planification> getQteDisp(Date date,MaterielFaculter m) {
		return PlanificationRepo.findByDateResSalleAndDemandeMatFacMatFaculter(date,m);
	}
	
}
