package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ActionRepository;
import com.GestiondesClub.entities.Action;

@Service
public class ActionService {

	@Autowired
	private ActionRepository actionRepo;

	public void deleteAction(long id) {
		actionRepo.deleteById(id);
	}

	public void updateAction(Action action) {
		actionRepo.save(action);
	}
	
}
