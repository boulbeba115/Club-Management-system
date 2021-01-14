package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ClubTarifInscriptionRepository;
import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.ClubTarifInscription;

@Service
public class ClubTarifationService {
	
	@Autowired
	private ClubTarifInscriptionRepository tarifClubRepo;

	public ClubTarifInscription clubTarifcation(String nomClub , AnneeUniversitaire annUniv) {
		return tarifClubRepo.findByAnneeUniverAnneeDebAndAnneeUniverAnneeFinAndClubNomClub(annUniv.getAnneeDeb(), annUniv.getAnneeFin(), nomClub);
	}

	public ClubTarifInscription updateTarif(ClubTarifInscription tarification) {
		return tarifClubRepo.save(tarification);
	}
	
	
}
