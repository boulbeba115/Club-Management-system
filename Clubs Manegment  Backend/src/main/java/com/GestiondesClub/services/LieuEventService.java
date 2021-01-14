package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.LieuRepository;
import com.GestiondesClub.entities.Lieu;

@Service
public class LieuEventService {
	@Autowired
	private LieuRepository lieuEvent;

	public Lieu ajoutLieu(Lieu l) {
		return lieuEvent.save(l);
	}
	public List<Lieu> getAllLieu() {
		return lieuEvent.findAll();
	}




	

}
