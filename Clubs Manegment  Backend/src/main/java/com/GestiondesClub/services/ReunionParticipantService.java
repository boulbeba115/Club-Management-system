package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ReunionParticipantRepository;
import com.GestiondesClub.entities.ReunionParticipant;

@Service
public class ReunionParticipantService {
	@Autowired
	private ReunionParticipantRepository ReunionParticipServ;

	public void saveParticipantReunion(ReunionParticipant leParticipant) {
		ReunionParticipServ.save(leParticipant);
	}

	public void presentAdmin(ReunionParticipant partadmin) {
		ReunionParticipServ.save(partadmin);
	}

}
