package com.GestiondesClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.Evenementdto;
import com.GestiondesClub.entities.DemandeEvenement;
import com.GestiondesClub.entities.Evenement;
import com.GestiondesClub.entities.EvenementTarif;
import com.GestiondesClub.entities.Tarifation;
import com.GestiondesClub.services.EvenementTarifService;
import com.GestiondesClub.services.TarifationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class EventTariController {
	@Autowired
	private EvenementTarifService eventTarifServ;
	@Autowired 
	private TarifationService tarifServ;
	
	@RequestMapping("/Tarif/list")
	public List<Tarifation> getAllTarifs()
	{
		return tarifServ.getAlltarifs();
	}
	@RequestMapping("/EventTarif/list")
	public List<EvenementTarif> getAllEventTarifs()
	{
		return eventTarifServ.getAllEventTarifs();
	}
	
	@PutMapping("/EventTarif/create")
	public EvenementTarif createClub(@RequestBody EvenementTarif eventTarif){
	return eventTarifServ.saveEventTarif(eventTarif);
	}
	@PutMapping("/Tarif/create")
	public Tarifation createClub(@RequestBody Tarifation tarif){
	return tarifServ.saveTarif(tarif);
	}
	
	@DeleteMapping("/eventTarif/Delete/{id}")
	public void supprimerTarif(@PathVariable long id){
		eventTarifServ.DeleteTarifEvent(id);
	}
}
