package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.VilleRepository;
import com.GestiondesClub.entities.Ville;

@Service
public class VilleService {
	@Autowired
	private VilleRepository villeRepo;

	public List<Ville> getAllVille() {
		return villeRepo.findAll();
	}

	public Optional<Ville> getVille(Long id) {
		return villeRepo.findById(id);
	}

	public Ville save(Ville ville) {
		return villeRepo.save(ville);
	}
	public Ville updateVille(Ville ville) {
		return villeRepo.save(ville);
	}

	public Ville findByVille(String ville) {
		return villeRepo.findByVille(ville);
	}
	
}
