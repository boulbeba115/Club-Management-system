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

import com.GestiondesClub.dto.EtudiantClubDao;
import com.GestiondesClub.entities.Etudiant;
import com.GestiondesClub.entities.MembresDeClub;
import com.GestiondesClub.entities.RoleEtudiant;
import com.GestiondesClub.services.EtudiantServices;
import com.GestiondesClub.services.MembreClubServ;
import com.GestiondesClub.services.RoleEtudiantServ;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class EtudiantController {
		
	@Autowired
	private EtudiantServices etudServ;
	
	@Autowired
	private RoleEtudiantServ roleEtudServ;
	
	@Autowired
	private MembreClubServ membreServ;
	
	/*@RequestMapping("/etudiant/list")
	public List<Etudiant> getAllEtud()
	{
		return etudServ.getAllEtud();
	}*/
	
	@RequestMapping("/etudiants/byReunion/{id}")
	public List<com.GestiondesClub.dto.RoleEtudiant> getEtudiantListByClub(@PathVariable long id)
	{
		return membreServ.getEtudiantListByClub(id);
	}
	
	@RequestMapping("/etudiant/WithClublist")
	public List<EtudiantClubDao> getetudiantClubList()
	{
		return etudServ.getetudiantClubList();
	}
	
	@RequestMapping("/etudiant/{id}")
	public Optional<Etudiant> getetud(@PathVariable Long id)
	{
		return etudServ.getetud(id);
	}
	
	@PutMapping("/etudiant/create")
	public Etudiant saveEtudiant (@RequestBody Etudiant etu){
	
		return etudServ.save(etu);	
	}
	
	@PutMapping("/etudiant/update")
	public Etudiant updateEtudiant(@RequestBody Etudiant etu){
	
		return etudServ.updateEtudiant(etu);	
	}
	
	@RequestMapping("/etudiant/findmat/{id}")
	public EtudiantClubDao findByMat(@PathVariable Long id)
	{
		return etudServ.findByMat(id);
	}
	@RequestMapping("/etudiant/findbylog/{id}")
	public EtudiantClubDao findByLog(@PathVariable String id)
	{
		return etudServ.findByLog(id);
	}
	
	@RequestMapping("/etudiant/roles/list")
	public List<RoleEtudiant>  findRoles()
	{
		return roleEtudServ.findRoles();
	}
	
}
