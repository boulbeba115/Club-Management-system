package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.DemCreationClubRepository;
import com.GestiondesClub.dto.CreeClubDto;
import com.GestiondesClub.entities.DemandeCreationClub;

@Service
public class DemCreationClubService {

	@Autowired
	private DemCreationClubRepository demandeCreationClub;

	public List<DemandeCreationClub> getAllDem() {
		return demandeCreationClub.findAll();
	}

	public Optional<DemandeCreationClub> getDem(Long id) {
		return demandeCreationClub.findById(id);
	}

	public DemandeCreationClub save(DemandeCreationClub d) {
		return demandeCreationClub.save(d);
	}

	public DemandeCreationClub updateDemande(DemandeCreationClub dem) {
		return demandeCreationClub.save(dem);
	}

	public List<CreeClubDto> getDem() {
		return demandeCreationClub.findAllProjectedBy();
	}
	

}
