package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.SponseurRepository;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Sponseur;

@Service
public class SponseurServ {
	@Autowired
	private SponseurRepository SponRepo;

	public List<Sponseur> GetAllSponseur() {
		return SponRepo.findAll();
	}

	public Optional<Sponseur> GetSponseur(Long id) {
		return SponRepo.findById(id);
	}

	public Sponseur saveSponseur(Sponseur sponseur) {
		return SponRepo.save(sponseur);
	}

	public List<Sponseur> GetAllActiveSponseur() {
		
		return SponRepo.findAllActivaSponByActiveTrue();
		
	}

	public List<Sponseur> getClubSponsor(Club c) {
		return SponRepo.findByLesEvenementEvenementDemandeEvenementLeClub(c);
	}
}
