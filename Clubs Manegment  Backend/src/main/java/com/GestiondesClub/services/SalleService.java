package com.GestiondesClub.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.SalleRepository;
import com.GestiondesClub.entities.Salle;

@Service
public class SalleService {
	@Autowired
	private SalleRepository salleRepo;

	public List<Salle> GetAllSalle() {
		return salleRepo.findAll();
	}

	public Optional<Salle> getSalle(Long id) {
		return salleRepo.findById(id);
	}

	public Salle createSalle(Salle salle) {
		return salleRepo.save(salle);
	}

	public Salle updateSalle(Salle salle) {
		return salleRepo.save(salle);
	}

	public List<Salle> getReservedSalle() {
		return salleRepo.findByLesDemandeLesplanificationDateResSalleOrLesReunionDateReunion(new Date(),new Date());
	}


	
}
