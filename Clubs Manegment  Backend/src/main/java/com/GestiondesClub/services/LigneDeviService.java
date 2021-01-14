package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.LigneDeviRepository;
import com.GestiondesClub.entities.LigneDevi;

@Service
public class LigneDeviService {

	@Autowired
	private LigneDeviRepository ligneDeviRepo;

	public LigneDevi addligndev(LigneDevi ld) {
		return ligneDeviRepo.save(ld);
	}
}
