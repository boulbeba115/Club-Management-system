package com.GestiondesClub.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.AnneUnivRepository;
import com.GestiondesClub.dto.LesInscritsClub;
import com.GestiondesClub.entities.AnneeUniversitaire;

@Service
public class AnneUnivService {

	@Autowired
	private AnneUnivRepository anneUnivRepo;

	public AnneeUniversitaire findYearUniv(int anneeDeb, int anneeFin) {
		return anneUnivRepo.findByAnneeDebAndAnneeFin(anneeDeb,anneeFin);
	}

	public List<AnneeUniversitaire> getUniverYears(int anneeCreation) {
	
		return anneUnivRepo.findByAnneeFinOrAnneeDeb(anneeCreation,anneeCreation);
	}

	public List<AnneeUniversitaire> getAllUniverYears() {
		return anneUnivRepo.findAll();
	}

	public AnneeUniversitaire findlastOne() {
		return anneUnivRepo.findFirstByOrderByIdDesc();
	}
}
