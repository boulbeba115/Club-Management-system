package com.GestiondesClub.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.DemandeAfficheRepository;
import com.GestiondesClub.entities.DemandeAffiche;

import antlr.collections.List;

@Service
public class DemandeAfficheServ {

	@Autowired
	private DemandeAfficheRepository demAffRepo;

	public DemandeAffiche ajoutDemAff(DemandeAffiche d) {
		return demAffRepo.save(d);
	}

	public Optional<DemandeAffiche> FindAffiche(Long id) {
		return demAffRepo.findById(id);
	}

}
