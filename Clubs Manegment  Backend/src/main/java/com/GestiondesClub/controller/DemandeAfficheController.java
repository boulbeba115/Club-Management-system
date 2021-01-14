package com.GestiondesClub.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GestiondesClub.dao.DemandeAfficheRepository;
import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.services.DemandeAfficheServ;

import antlr.collections.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class DemandeAfficheController {
	
	@Autowired
	private DemandeAfficheServ demAfficheServ;
	
	@RequestMapping(value="demandeAffiche/upload", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public String uploadFile(@RequestParam("file") MultipartFile file ) throws IOException {
		String imgname = UUID.randomUUID().toString()+'-'+file.getOriginalFilename();
		File convertFile = new File("C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\LesDemandeAffiche\\"+imgname);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return imgname;
	}
	
	@PutMapping("demandeAffiche/Confirme")
	public DemandeAffiche ConfirmAffiche(@RequestBody DemandeAffiche affiche)
	{
		 return demAfficheServ.ajoutDemAff(affiche);
	}
	@PutMapping("demandeAffiche/UpdateAffiche")
	public void updateDemAffiche(@RequestBody DemandeAffiche affiche)
	{
		Optional<DemandeAffiche> demAff = demAfficheServ.FindAffiche(affiche.getId());
		if(demAff.get()==null)
			return ;
		DemandeAffiche da = demAff.get();
		da.setDemandeEcrite(affiche.getDemandeEcrite());
		da.setDemandeImage(affiche.getDemandeImage());
		System.out.println(da.toString());
		demAfficheServ.ajoutDemAff(da);
	}

	

}
