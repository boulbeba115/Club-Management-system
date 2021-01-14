package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ReunionRepository;
import com.GestiondesClub.dto.ReunionDto;
import com.GestiondesClub.entities.Inviter;
import com.GestiondesClub.entities.Reunion;

@Service
public class ReunionServ {

	@Autowired
	private ReunionRepository reunionRepo;

	public List<ReunionDto> gatAllReunion() {
		return reunionRepo.findAllBy();
	}

	public Reunion saveReunion(Reunion r) {
		return  reunionRepo.save(r);
	}

	public List<ReunionDto> findByClubName(String clubName) {
		return reunionRepo.findByLeClubNomClub(clubName) ;
	}
	 
}
