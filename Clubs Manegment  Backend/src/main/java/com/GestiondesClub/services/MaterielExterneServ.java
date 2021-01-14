package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.MaterielExterneRepository;
import com.GestiondesClub.entities.MaterielExterne;

@Service
public class MaterielExterneServ {
	@Autowired
	private MaterielExterneRepository MatExtRepo;

	public List<MaterielExterne> getAllMatExt() {
		return MatExtRepo.findAll();
	}

	public Optional<MaterielExterne> getMatExt(Long id) {
		return MatExtRepo.findById(id);
	}

	public MaterielExterne saveMatExt(MaterielExterne matExter) {
		return MatExtRepo.save(matExter);
	}
	

}
