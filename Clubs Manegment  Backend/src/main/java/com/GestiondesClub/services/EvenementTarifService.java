package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.EvenementTarifationRepository;
import com.GestiondesClub.entities.EvenementTarif;

@Service
public class EvenementTarifService {

	@Autowired
	private EvenementTarifationRepository eventTarifRepo;

	public List<EvenementTarif> getAllEventTarifs() {
		return eventTarifRepo.findAll();
	}

	public EvenementTarif saveEventTarif(EvenementTarif eventTarif) {
		return eventTarifRepo.save(eventTarif);
	}

	public void DeleteTarifEvent(long id) {
		eventTarifRepo.deleteById(id);
		
	}
}
