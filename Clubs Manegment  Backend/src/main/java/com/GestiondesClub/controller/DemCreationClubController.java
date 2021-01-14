package com.GestiondesClub.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.CreeClubDto;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.ClubTarifInscription;
import com.GestiondesClub.entities.DemandeCreationClub;
import com.GestiondesClub.entities.Mail;
import com.GestiondesClub.entities.MembreFondateur;
import com.GestiondesClub.services.AnneUnivService;
import com.GestiondesClub.services.ClubService;
import com.GestiondesClub.services.ClubTarifationService;
import com.GestiondesClub.services.DemCreationClubService;
import com.GestiondesClub.services.EmailService;
import com.GestiondesClub.services.MembreClubServ;
import com.GestiondesClub.services.MembreFondateurService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class DemCreationClubController {

	@Autowired
	private DemCreationClubService demCreClub ;
	@Autowired
	private MembreFondateurService membFondaServ ;
	@Autowired
	private MembreClubServ membreDeClubServ;
	@Autowired 
	private ClubService clubServ;
	@Autowired
	private EmailService mailServ;
	@Autowired
	private ClubTarifationService tarifInscServ; 
	@Autowired
	private AnneUnivService anneServ;
	/*@RequestMapping("/demandecreationclub/list")
	public List<DemandeCreationClub> getAllDemandes()
	{
		return demCreClub.getAllDem();
	}*/
	
	
	@RequestMapping("/demandecreationclub/list")
	public List<CreeClubDto> getDem()
	{
		return demCreClub.getDem();
	}
	
	
	@RequestMapping("/demandecreationclub/{id}")
	public Optional<DemandeCreationClub> getDemande(@PathVariable Long id)
	{
		return demCreClub.getDem(id);
	}
	
	@PutMapping("/demandecreationclub/create")
	public void createDemande(@RequestBody DemandeCreationClub dem){
		DemandeCreationClub d ;
		dem.setDatedemande(new Date());
		d = demCreClub.save(dem);	

			
		for(MembreFondateur m : dem.getMembresFondateur())
		{
			m.setDemandeCreationClub(d);
			membFondaServ.saveMbreFonda(m);
		}
		

	}
	
	@PutMapping("/demandecreationclub/update")
	public DemandeCreationClub updateDemande(@RequestBody DemandeCreationClub dem){
	
		return demCreClub.updateDemande(dem);	
	}
	
	@PutMapping("/demandecreationclub/Confirm")
	public DemandeCreationClub confirmDemande(@RequestBody DemandeCreationClub dem){
		
		if(dem.getId()==null)
			return null;
		DemandeCreationClub d = demCreClub.getDem(dem.getId()).get();
		 d.setConfirmer(dem.getConfirmer());
		 d = demCreClub.save(d);
		if(d==null)
		return null;
		Club c = new Club(); 
		c.setNomClub(dem.getNomClub());
		c.setDateCreation(new Date());
		c.setActivityStop(false);
		c.setClubLogo("no_Image.jpg");
		c.setCouvertureClub("cover-default.jpg");
		c.setDemandeCreationClub(d);
		Club newClub = clubServ.save(c);
		if(newClub == null)
		return null;
		for(MembreFondateur membFonda : dem.getMembresFondateur())
		{
			membreDeClubServ.saveMembre(membFonda,newClub);
		}
		 ClubTarifInscription tr = new ClubTarifInscription();
		 tr.setClub(newClub);
		 tr.setTarifation(0);
		 tr.setAnneeUniver(anneServ.findlastOne());
		 tarifInscServ.updateTarif(tr);
		 
		Mail m = new Mail();
		m.setContent("Votre Demande de Club "+dem.getNomClub()+" a  été accepter vous peuvait Maintenamt accéder"
				+ " au interface est mise a jour votre club est crée des évenement");
		m.setFrom("systemgestionclubfsegs@gmail.com");
		m.setSubject("Confirmation de création de Club");
		m.setTo("boulbebazz@yahoo.fr");
		//mailServ.sendSimpleMessage(m);
		return null;
	}
	
}
