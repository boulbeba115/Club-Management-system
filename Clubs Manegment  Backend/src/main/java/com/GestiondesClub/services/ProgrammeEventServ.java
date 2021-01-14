package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ProgrammeEventDao;
import com.GestiondesClub.dto.EventAllAffiche;
import com.GestiondesClub.entities.ProgrammeEvent;

@Service
public class ProgrammeEventServ {
	
	@Autowired
	private ProgrammeEventDao progEventRepo;

	public void SaveProg(ProgrammeEvent progEvent) {
		 progEventRepo.save(progEvent);
	}

	public ProgrammeEvent findProgAction(long id) {
			return progEventRepo.findByLesActionId(id);
	}

	public void update(ProgrammeEvent e) {
		 progEventRepo.save(e);		
	}


	
	
	
}
