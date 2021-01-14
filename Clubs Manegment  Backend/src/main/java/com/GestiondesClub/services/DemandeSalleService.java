package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.DemandeSalleRepository;
import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.Salle;

@Service
public class DemandeSalleService {
	@Autowired
		private DemandeSalleRepository demandeSalleRepo;

	public void affecter(DemmandeSalle d) {
		 demandeSalleRepo.save(d);
	}
	
	public List<DemmandeSalle> findallDemande(Salle s)
	{
		return demandeSalleRepo.findBysalle(s);
	}
	public Optional<DemmandeSalle> findbyid(Long id)
	{
		return demandeSalleRepo.findById(id);
	}

	
}
