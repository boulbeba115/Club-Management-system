package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ReunionInviterRepository;
import com.GestiondesClub.entities.ReunionInviter;

@Service
public class ReunionInviterService {
	
	@Autowired
	private ReunionInviterRepository reunionInviterRepo;

	public void saveReunionInviter(ReunionInviter inviterReunion) {
		reunionInviterRepo.save(inviterReunion);
		
	}

	public void presentInviter(ReunionInviter invited) {
		reunionInviterRepo.save(invited);
	}
	

}
