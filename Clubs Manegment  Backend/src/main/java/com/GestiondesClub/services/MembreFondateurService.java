package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.MembreFondateurRepository;
import com.GestiondesClub.entities.MembreFondateur;

@Service
public class MembreFondateurService {
	@Autowired
		private MembreFondateurRepository membreFondaRepo;

	public void saveMbreFonda(MembreFondateur m) {
		 membreFondaRepo.save(m);
	}

}
