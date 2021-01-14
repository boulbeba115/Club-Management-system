package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.MaterielFaculterRepository;
import com.GestiondesClub.dao.MaterielRepository;
import com.GestiondesClub.entities.Materiel;
import com.GestiondesClub.entities.MaterielFaculter;

@Service
public class MaterialService {
	@Autowired 
	private MaterielFaculterRepository materialfacRepo;

	public List<MaterielFaculter> getAllMatFac() {
		return materialfacRepo.findAll();
	}

	public Optional<MaterielFaculter> getMatFac(Long id) {
		return materialfacRepo.findById(id);
	}

	public MaterielFaculter save(MaterielFaculter matfac) {
		return materialfacRepo.save(matfac);
	}
	
	
	
}
