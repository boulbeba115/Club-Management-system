package com.GestiondesClub.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.GestiondesClub.dto.Clubdto;
import com.GestiondesClub.dto.DtoClub;
import com.GestiondesClub.dto.EtudiantClubDao;
import com.GestiondesClub.dto.OnlyClubDto;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.ClubTarifInscription;
import com.GestiondesClub.entities.Utilisateur;
import com.GestiondesClub.services.AnneUnivService;
import com.GestiondesClub.services.ClubService;
import com.GestiondesClub.services.ClubTarifationService;
import com.GestiondesClub.services.EtudiantServices;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class ClubController {
	@Autowired
	private ClubService clubService ;
	@Autowired
	private ClubTarifationService tarifInscServ; 
	@Autowired
	private EtudiantServices etudSer;
	@Autowired
	private AnneUnivService anneServ;
	
	@RequestMapping("/club/list")
	public List<Clubdto> getAllClub()
	{
		return clubService.getAllClub();
	}
	@RequestMapping("/club/only/list/{id}")
	public List<DtoClub> getOnlyClub(@PathVariable String id)
	{	
		EtudiantClubDao e = etudSer.findByLog(id);
		List<Club> finalList = new ArrayList<Club>() ;
		List<Club> studenClub = new ArrayList<Club>() ;
		int i;
		List<OnlyClubDto> etudiantClub = clubService.getEtudiantClub(e.getId());
		List<OnlyClubDto> tousLesClub = clubService.getOnlyClub();
		tousLesClub.forEach(club ->{
		Club c = new Club(club.getId(),club.getNomClub(),club.getDateCreation(),club.getClubLogo(),club.getActivityStop(),
				club.getInscriptionOn(),club.getCouvertureClub(),club.getTermsofUse());
		c.setTarifInscription(club.getTarifInscription());
		finalList.add(c);
		});
		etudiantClub.forEach(club ->{
		Club c = new Club(club.getId(),club.getNomClub(),club.getDateCreation(),club.getClubLogo(),club.getActivityStop(),
				club.getInscriptionOn(),club.getCouvertureClub(),club.getTermsofUse());
		c.setTarifInscription(club.getTarifInscription());
		studenClub.add(c);
		});
		List<DtoClub> lesClubs = new ArrayList<DtoClub>() ; 
		finalList.removeAll(studenClub);
		for(Club club:finalList) {
		DtoClub dtoClub = new DtoClub(club.getId(),club.getNomClub(),club.getDateCreation(),club.getClubLogo(),club.getActivityStop(),
		club.getInscriptionOn(),club.getCouvertureClub(),club.getTermsofUse());
		dtoClub.setTarifInscription(club.getTarifInscription());
		dtoClub.setIsMember(false);
		lesClubs.add(dtoClub);
		}
		for(Club club:studenClub) {
		DtoClub dtoClub = new DtoClub(club.getId(),club.getNomClub(),club.getDateCreation(),club.getClubLogo(),club.getActivityStop(),
				club.getInscriptionOn(),club.getCouvertureClub(),club.getTermsofUse());
		dtoClub.setTarifInscription(club.getTarifInscription());
		dtoClub.setIsMember(true);
		lesClubs.add(dtoClub);
		}
		
		return lesClubs;
	}
	@RequestMapping("/club/tarification/{id}")
	public ClubTarifInscription clubTarifcation(@PathVariable String id , @RequestBody AnneeUniversitaire anneUnv)
	{
		AnneeUniversitaire anneUniver = anneServ.findYearUniv(anneUnv.getAnneeDeb(), anneUnv.getAnneeFin());
		if(anneUniver==null)
		return null; 
		ClubTarifInscription clubTarif=  tarifInscServ.clubTarifcation(id,anneUnv);
		 if(clubTarif!=null)
		 return clubTarif;
		 else
		 {
			 ClubTarifInscription tr = new ClubTarifInscription();
			 tr.setClub(clubService.finClubByName(id));
			 tr.setTarifation(0);
			 tr.setAnneeUniver(anneUniver);
			 return tarifInscServ.updateTarif(tr);
		 }
		 
	}
	
	@PutMapping("/club/update/tarification")
	public ClubTarifInscription updateTarification(@RequestBody ClubTarifInscription tarification)
	{
		return tarifInscServ.updateTarif(tarification);
	}
	 
	@RequestMapping("/club/ClubWithMember/list")
	public List<ClubMembreDto> findAllClubWithMember()
	{
		return clubService.findAllClubWithMember();
	}
	
	@RequestMapping("/club/byYear/{id}")
	public ClubMembreDto findClubByYear(@PathVariable String id , @RequestBody AnneeUniversitaire anneUnv)
	{
		return clubService.findClubByYear(anneUnv,id);
	}
	
	@RequestMapping("/club/{id}")
	public Optional<Club> getClub(@PathVariable Long id)
	{
		return clubService.getClub(id);
	}
	
	@PutMapping("/club/create")
	public Club createClub(@RequestBody Club club){
	
		return clubService.save(club);	
	}
	
	@PutMapping("/club/activation")
	public void updateClub(@RequestBody Club club){
		club.setActivityStop(!club.getActivityStop());
		 clubService.updateClub(club);	
	}
	@PutMapping("/club/inscriptionActiv")
	public void inscriptionActiv(@RequestBody Club club){
		Club c = clubService.getClub(club.getId()).get();
		c.setInscriptionOn(club.getInscriptionOn());
		 clubService.updateClub(c);	
	}
	@PutMapping("/club/tearmsInscription")
	public void tearmsInscription(@RequestBody Club club){
		Club c = clubService.getClub(club.getId()).get();
		c.setTermsofUse(club.getTermsofUse());
		System.out.println(c.getTermsofUse());
	    clubService.updateClub(c);	
	}
	@RequestMapping("/club/byname/{id}")
	public ClubMembreDto getByName(@PathVariable String id)
	{
		return clubService.getByName(id);
	}
	@RequestMapping(value="club/logo/{id}", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public String uploadFile(@PathVariable long id,@RequestParam("file") MultipartFile file ) throws IOException {
		String imgname = UUID.randomUUID().toString()+'-'+file.getOriginalFilename();
		Club club = clubService.getClub(id).get();
		File convertFile = new File("C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\ClubLogos\\"+imgname);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		club.setClubLogo(imgname);
		clubService.updateClub(club);	
		return imgname;
	}
	@RequestMapping(value="club/cover/{id}", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public String uploadCover(@PathVariable long id,@RequestParam("file") MultipartFile file ) throws IOException {
		String imgname = UUID.randomUUID().toString()+'-'+file.getOriginalFilename();
		Club club = clubService.getClub(id).get();
		File convertFile = new File("C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\ClubCover\\"+imgname);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		club.setCouvertureClub(imgname);
		clubService.updateClub(club);	
		return imgname;
	}
}
