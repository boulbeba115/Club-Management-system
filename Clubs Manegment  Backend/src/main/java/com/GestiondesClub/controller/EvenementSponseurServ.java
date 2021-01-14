package com.GestiondesClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.EvenementSponseurRepository;
import com.GestiondesClub.entities.EvenementSponseur;

@Service
public class EvenementSponseurServ {

	@Autowired
	private EvenementSponseurRepository eventSponDao;

	public  EvenementSponseur AddSponsorisation(EvenementSponseur eventSpon) {
		return eventSponDao.save(eventSpon);
	}
	
}
