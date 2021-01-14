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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Sponseur;
import com.GestiondesClub.entities.TypeSalle;
import com.GestiondesClub.services.ClubService;
import com.GestiondesClub.services.SponseurServ;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class SponController {
	@Autowired
	private SponseurServ sponServ;
	@Autowired ClubService clubServ;
	@RequestMapping("/Sponseur/list")
	public List<Sponseur> GetAllSponseur()
	{
		return sponServ.GetAllSponseur();
	}
	
	@RequestMapping("/ClubSponsor/list/{id}")
	public List<Sponseur> getClubSponsor(@PathVariable String id)
	{
		ClubMembreDto cdto = clubServ.getByName(id);
		Club club = new Club();
		club.setId(cdto.getId());
		return sponServ.getClubSponsor(club);
	}
	
	@RequestMapping("/Sponseur/Active/list")
	public List<Sponseur> GetAllActiveSponseur()
	{
		return sponServ.GetAllActiveSponseur();
	}
	
	@RequestMapping("/Sponseur/{id}")
	public Optional<Sponseur> GetSponseur(@PathVariable Long id)
	{
		return sponServ.GetSponseur(id);
	}
	
	@PutMapping("/Sponseur/create")
	public Sponseur saveSponseur(@RequestBody Sponseur sponseur){
	
		return sponServ.saveSponseur(sponseur);	
	}
	
	@RequestMapping(value="Sponseur/logo", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public String uploadFile(@RequestParam("file") MultipartFile file ) throws IOException {
		String imgname = UUID.randomUUID().toString()+'-'+file.getOriginalFilename();
		File convertFile = new File("C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\sponseurlogo\\"+imgname);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return imgname;
	}

}
