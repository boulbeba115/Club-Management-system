package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.TarifRepository;
import com.GestiondesClub.entities.Tarifation;

@Service
public class TarifationService {

	@Autowired
	private TarifRepository tarifRepo;

	public List<Tarifation> getAlltarifs() {
		return tarifRepo.findAll();
	}

	public Tarifation saveTarif(Tarifation tarif) {
		return tarifRepo.save(tarif);
	}

	public void DeleteTarif(long id) {
		tarifRepo.deleteById(id);
	}
	
}
