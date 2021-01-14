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

import com.GestiondesClub.entities.TypeClub;
import com.GestiondesClub.services.TypeClubService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class TypeClubController {
	@Autowired
	private TypeClubService typeServ;
	
	@RequestMapping("/Type/list")
	public List<TypeClub> getAllType()
	{
		return typeServ.getAllType();
	}
	
	
	@RequestMapping("/Type/{id}")
	public Optional<TypeClub> getType(@PathVariable Long id)
	{
		return typeServ.getType(id);
	}
	
	@PutMapping("/Type/create")
	public TypeClub createType(@RequestBody TypeClub type){
	
		return typeServ.save(type);	
	}
	
	@PutMapping("/Type/update")
	public TypeClub updateClub(@RequestBody TypeClub type){
	
		return typeServ.updateClub(type);	
	}
	@DeleteMapping("/Type/update/{id}")
	public boolean deleteType(@PathVariable long id){
		typeServ.delete(id);
		return true;
	}
}
