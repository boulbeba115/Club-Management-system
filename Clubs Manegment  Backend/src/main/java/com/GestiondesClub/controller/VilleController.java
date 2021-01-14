package com.GestiondesClub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.entities.Ville;
import com.GestiondesClub.services.VilleService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class VilleController {
	@Autowired 
	private VilleService villeServ;
	
	
	@RequestMapping("/Ville/list")
	public List<Ville> getAllVille()
	{
		return villeServ.getAllVille();
	}
	
	@RequestMapping("/Ville/{id}")
	public Optional<Ville> getVille(@PathVariable Long id)
	{
		return villeServ.getVille(id);
	}
	@RequestMapping("/findVille/{id}")
	public Ville findByVille(@PathVariable String id)
	{
		return villeServ.findByVille(id);
	}
	@PutMapping("/Ville/create")
	public Ville createType(@RequestBody Ville ville){
	
		return villeServ.save(ville);	
	}
	
	@PutMapping("/Ville/update")
	public Ville updateVille(@RequestBody Ville ville){
	
		return villeServ.updateVille(ville);	
	}
	

}
