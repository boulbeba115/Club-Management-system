package com.GestiondesClub.services;

import java.io.Console;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.EtudiantRepository;
import com.GestiondesClub.dto.EtudiantClubDao;
import com.GestiondesClub.entities.Etudiant;

@Service
public class EtudiantServices {
	
	@Autowired
	private EtudiantRepository etudRepo;

	public List<Etudiant> getAllEtud() {
		return etudRepo.findAll();
	}

	public Optional<Etudiant> getetud(Long id) {
		return etudRepo.findById(id);
	}

	public Etudiant save(Etudiant etu) {
		return etudRepo.save(etu);
	}

	public Etudiant updateEtudiant(Etudiant etu) {
		return etudRepo.save(etu);
	}

	public EtudiantClubDao findbymat(Long id) {
		return etudRepo.findByMatricule(id);
	}

	public EtudiantClubDao findByMat(Long id) {
		return etudRepo.findByMatricule(id);
	}

	public EtudiantClubDao findByLog(String id) {
		return etudRepo.findByLelogin(id);
	}

	public List<EtudiantClubDao> getetudiantClubList() {
		return  etudRepo.findEtudiantClubBy();
	}


	
	
	

}
