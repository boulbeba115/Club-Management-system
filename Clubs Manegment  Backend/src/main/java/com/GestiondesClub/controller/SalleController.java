package com.GestiondesClub.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.PlanificationSalleDto;
import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.Planification;
import com.GestiondesClub.entities.Salle;
import com.GestiondesClub.services.DemandeSalleService;
import com.GestiondesClub.services.PlanificationService;
import com.GestiondesClub.services.SalleService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class SalleController {
	@Autowired
	private SalleService salleServ;
	@Autowired
	private DemandeSalleService demserv;
	@Autowired
	private PlanificationService planifServ;
	
	@RequestMapping("/Salle/list")
	public List<Salle> GetAllSalle()
	{
		return salleServ.GetAllSalle();
	}
	
	@RequestMapping("/Reserved/Salle/list")
	public List<Salle> getReservedSalle()
	{
		return salleServ.getReservedSalle() ;
	}
	
	@RequestMapping("/Salle/listWithPlan")
	public List<DemmandeSalle> salleplanif()
	{
		Optional<Salle> sall ;
		sall= salleServ.getSalle((long) 1);
		return demserv.findallDemande(sall.get());
	}
	
	@RequestMapping("/Salle/Planif/list/{id}")
	public List<PlanificationSalleDto> findplanif(@PathVariable String id) throws ParseException
	{
		java.util.Date date =new SimpleDateFormat("yyyy-MM-dd").parse(id);  
		if(date==null)
			return null;
		return planifServ.findplanif(date);
	}
	
	@RequestMapping("/Salle/{id}")
	public Optional<Salle> getSalle(@PathVariable Long id)
	{
		return salleServ.getSalle(id);
	}
	
	@PutMapping("/Salle/create")
	public Salle createSalle(@RequestBody Salle salle){
	
		return salleServ.createSalle(salle);	
	}
	@PutMapping("/Salle/update")
	public Salle updateSalle(@RequestBody Salle salle){
	
		return salleServ.updateSalle(salle);	
	}
	
	
}
