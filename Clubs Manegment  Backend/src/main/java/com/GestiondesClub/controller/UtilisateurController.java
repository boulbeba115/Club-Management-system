package com.GestiondesClub.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GestiondesClub.dto.AdminDto;
import com.GestiondesClub.dto.UtilisateurDto;
import com.GestiondesClub.entities.Administration;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Etudiant;
import com.GestiondesClub.entities.Utilisateur;
import com.GestiondesClub.services.AdministrationService;
import com.GestiondesClub.services.UtilisateurService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")

public class UtilisateurController {
	@Autowired
	private UtilisateurService userServ;
	@Autowired 
	private AdministrationService adminServ;
	
	@RequestMapping("/Administration/list")
	public List<AdminDto> getAllAdmins()
	{
		return adminServ.getAllAdmins();
	}
	
	@RequestMapping("/utilisateur/list")
	public List<Utilisateur> getAllUsers()
	{
		return userServ.getAllUsers();
	}
	@PutMapping("/utilisateur/update")
	public boolean updateUsers(@RequestBody UtilisateurDto user )
	{	Utilisateur u = new Utilisateur();
		u.setMotPass(user.getOldMotPass());
		u.setLelogin(user.getLelogin());
		if(userServ.login(u))
		{
			Utilisateur util= userServ.findUserBylogin(user.getLelogin());
			util.setAdresse(user.getAdresse());
			util.setEmail(user.getEmail());
			util.setTel(user.getTel());
			if(user.getMotPass()==null)
			util.setMotPass(user.getMotPass());
			userServ.updateUsers(util);
			return true;
		}
		else
			return false;
	}
	@PostMapping("/login")
	public String login(@RequestBody Utilisateur user){
		Utilisateur u = new  Utilisateur();
		String leRole = new String();
		if(userServ.login(user)==true)
		{
			u= userServ.getUser(user);
			if(u instanceof Administration)
			{
				Administration admin = (Administration) u; 
				leRole= admin.getRole();
				return leRole;

			}
			else
			if(u instanceof Etudiant)
			{
				//Etudiant etudiant = (Etudiant) u; 
				leRole= "etudiant";
				return leRole;

			}
			return leRole;
		}
		else
		{
			return "no_acces";
		}
	}
	
	@RequestMapping("/utilisateur/{id}")
	public Utilisateur getClub(@PathVariable String id)
	{
		return userServ.findUserBylogin(id);
	}
	
	
	@RequestMapping(value="User/logo/{id}", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public String uploadFile(@PathVariable long id,@RequestParam("file") MultipartFile file ) throws IOException {
		Utilisateur user = userServ.findUserByid(id).get();
		String imgname = UUID.randomUUID().toString()+'-'+file.getOriginalFilename();
		user.setUtilisateurAvatar(imgname);
		File convertFile = new File("C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\membrePhoto\\"+imgname);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		userServ.updateUsers(user);
		return imgname;
	}
	
}
