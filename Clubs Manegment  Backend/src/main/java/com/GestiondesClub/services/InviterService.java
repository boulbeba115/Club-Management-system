package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.InviterRepository;
import com.GestiondesClub.dto.Clubdto;
import com.GestiondesClub.entities.Inviter;

@Service
public class InviterService {
	@Autowired
	private InviterRepository invitServ;

	public Inviter getInviterByCin(int cin) {
		return invitServ.findByCin(cin);
	}

	public Inviter saveInvited(Inviter i) {
		return invitServ.save(i);
	}

}
