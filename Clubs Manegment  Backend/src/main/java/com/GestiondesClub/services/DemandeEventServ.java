package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.DemandeAfficheRepository;

@Service
public class DemandeEventServ {
	
	@Autowired 
	private DemandeAfficheRepository demAfficheDao;
}
