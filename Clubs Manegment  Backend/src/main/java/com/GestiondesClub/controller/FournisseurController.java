package com.GestiondesClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.entities.Fournisseur;
import com.GestiondesClub.services.FournisseurService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class FournisseurController {

	@Autowired
	private FournisseurService fournServ;
	
	@GetMapping("/fournisseur/list")
	public List<Fournisseur> getAllFournisseur() {
		return fournServ.getAllFournisseur();
	}
	@PutMapping("/fournisseur/add")
	public Fournisseur addFournisseur(@RequestBody Fournisseur f) {
		return fournServ.addFournisseur(f);
	}
}
