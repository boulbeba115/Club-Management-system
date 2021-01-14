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

import com.GestiondesClub.entities.TypeSalle;
import com.GestiondesClub.services.TypeSalleService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class TypeSalleController {
	
	@Autowired
		private TypeSalleService typeSalleServ;
	
	@RequestMapping("/TypeSalle/list")
	public List<TypeSalle> GetAllTypeSalle()
	{
		return typeSalleServ.GetAllTypeSalle();
	}
	
	@RequestMapping("/TypeSalle/{id}")
	public Optional<TypeSalle> getTypeSalle(@PathVariable Long id)
	{
		return typeSalleServ.getTypeSalle(id);
	}
	
	@PutMapping("/TypeSalle/create")
	public TypeSalle createTypeSalle(@RequestBody TypeSalle typesalle){
	
		return typeSalleServ.createTypeSalle(typesalle);	
	}
	@PutMapping("/TypeSalle/update")
	public TypeSalle updateTypeSalle(@RequestBody TypeSalle typesalle){
	
		return typeSalleServ.updateTypeSalle(typesalle);	
	}
	
	

}
