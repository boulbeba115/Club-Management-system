package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.FournisseurRepository;
import com.GestiondesClub.entities.Fournisseur;

@Service
public class FournisseurService {

	@Autowired
	private FournisseurRepository fournissRepos;

	public List<Fournisseur> getAllFournisseur() {
		return fournissRepos.findAll();
	}

	public Fournisseur addFournisseur(Fournisseur f) {
		return fournissRepos.save(f);
	}
}
