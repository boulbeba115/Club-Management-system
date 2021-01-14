package com.GestiondesClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.Evenementdto;
import com.GestiondesClub.services.EvenementService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class InscriptionEventController {
	
	@Autowired 
	private EvenementService eventServ;
	
	@RequestMapping("/event/NotExp/All/list")
	public List<Evenementdto> getAllNotExp()
	{
		return eventServ.getAllNotExp();
	}
	
}
