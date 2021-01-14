package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.SponsarisationMaterielleRepo;
import com.GestiondesClub.entities.SponsarisationMateriel;

@Service
public class SponsarisationMaterielleServ {

	@Autowired
	private SponsarisationMaterielleRepo sponMatRepo;

	public void saveSponMat(SponsarisationMateriel sm) {
		sponMatRepo.save(sm);
	}
}
