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

import com.GestiondesClub.entities.MaterielExterne;
import com.GestiondesClub.entities.Sponseur;
import com.GestiondesClub.services.MaterielExterneServ;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class MaterielExterneController {
	@Autowired
	private MaterielExterneServ matExtServ;
	
	@RequestMapping("/MatExt/list")
	public List<MaterielExterne> getAllMatExt()
	{
		return matExtServ.getAllMatExt();
	}
	
	@RequestMapping("/MatExt/{id}")
	public Optional<MaterielExterne> getMatExt(@PathVariable Long id)
	{
		return matExtServ.getMatExt(id);
	}
	
	@PutMapping("/MatExt/create")
	public MaterielExterne saveSponseur(@RequestBody MaterielExterne matExter){
	
		return matExtServ.saveMatExt(matExter);	
	}
	

}
