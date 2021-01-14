package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dto.ParticipantEtudiantRepository;
import com.GestiondesClub.entities.ParticipantEtudiant;

@Service
public class ParticipEtudiantServ {

	@Autowired
	private ParticipantEtudiantRepository partetudRepo;

	public void saveEtudiantPart(ParticipantEtudiant partEtudiant) {
		partetudRepo.save(partEtudiant);
	}

	public void presentMember(ParticipantEtudiant pe) {
		partetudRepo.save(pe);		
	}
}
