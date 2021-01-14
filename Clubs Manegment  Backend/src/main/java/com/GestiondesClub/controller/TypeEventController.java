package com.GestiondesClub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.entities.TypeEvenement;
import com.GestiondesClub.services.TypeEventServices;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class TypeEventController {
	
	@Autowired
	private TypeEventServices typeEvSer;
	
	@RequestMapping("/TypeEvent/list")
	public List<TypeEvenement> getAllType()
	{
		return typeEvSer.getAllType();
	}
	
	@RequestMapping("/TypeEvent/{id}")
	public Optional<TypeEvenement> getType(@PathVariable Long id)
	{
		return typeEvSer.getType(id);
	}
	
	@PutMapping("/TypeEvent/create")
	public TypeEvenement createType(@RequestBody TypeEvenement type){
	
		return typeEvSer.save(type);	
	}
	
	@PutMapping("/TypeEvent/update")
	public TypeEvenement updateType(@RequestBody TypeEvenement type){
	
		return typeEvSer.updateType(type);	
	}
	@DeleteMapping("/TypeEvent/delete/{id}")
	public boolean deleteType(@PathVariable long id){
		typeEvSer.delete(id);
		return true;
	}

}
