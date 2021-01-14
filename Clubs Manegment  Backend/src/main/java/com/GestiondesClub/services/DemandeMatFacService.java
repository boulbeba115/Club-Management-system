package com.GestiondesClub.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.DemandeMaterielFacRepository;
import com.GestiondesClub.entities.DemandeMaterielFac;

@Service
public class DemandeMatFacService {
	@Autowired
	private DemandeMaterielFacRepository demMatRepo;

	public void affecter(DemandeMaterielFac d) {
		 demMatRepo.save(d);
	}

	public Optional<DemandeMaterielFac> findDemandeMat(long l) {

		return demMatRepo.findById(l);
	}
	

}
