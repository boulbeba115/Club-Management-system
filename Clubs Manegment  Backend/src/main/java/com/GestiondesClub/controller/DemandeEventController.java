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

import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.dto.DemEventDto;
import com.GestiondesClub.dto.LesMatARes;
import com.GestiondesClub.dto.LesSalleAReserve;
import com.GestiondesClub.dto.NotifOnly;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeCreationClub;
import com.GestiondesClub.entities.DemandeEvenement;
import com.GestiondesClub.entities.DemandeMaterielFac;
import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.Lieu;
import com.GestiondesClub.entities.MaterielFaculter;
import com.GestiondesClub.entities.Planification;
import com.GestiondesClub.entities.Salle;
import com.GestiondesClub.services.ClubService;
import com.GestiondesClub.services.DemEventServices;
import com.GestiondesClub.services.DemandeMatFacService;
import com.GestiondesClub.services.DemandeSalleService;
import com.GestiondesClub.services.LieuEventService;
import com.GestiondesClub.services.MaterialService;
import com.GestiondesClub.services.PlanificationService;
import com.GestiondesClub.services.VilleService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class DemandeEventController {
	@Autowired
	private DemEventServices demandeEvServ;
	@Autowired
	private LieuEventService LieuEvService;
	@Autowired
	private VilleService villeService;
	@Autowired
	private DemandeSalleService demandeSalleServ;
	@Autowired 
	private PlanificationService PlanifcationServ;
	@Autowired
	private DemandeMatFacService demandeMatFacServ;
	@Autowired
	private ClubService clubService ;
	@Autowired
	private MaterialService materiaLServ;
	
	private List<Salle> salles;
	@RequestMapping("/DemandeEvent/list")
	public List<DemEventDto> GetAllDemande()
	{
		return demandeEvServ.GetAllDemande();
	}
	
	@RequestMapping("/DemandeEvent/{id}")
	public Optional<DemandeEvenement> getDemandeEvent(@PathVariable Long id)
	{
		return demandeEvServ.getDemandeEvent(id);
	}
	
	@RequestMapping("/demandecreationclub/listbyClub/{id}")
	public List<DemEventDto> getAllByclub(@PathVariable String id)
	{
		ClubMembreDto c = clubService.getByName(id);
		Club leclub = new Club();
		leclub.setId(c.getId());
		leclub.setNomClub(c.getNomClub());
		return demandeEvServ.getAllByclub(leclub);
	}
	
	@PutMapping("/DemandeEvent/create")
	public DemandeEvenement createDemandeEvent(@RequestBody DemandeEvenement demande){
		Lieu l = new Lieu();
		salles = null;
		if(!demande.isExterne())
		{
			l.setId((long) 1);
			l.setSite("faculte de science economique et gestion sfax");
			l.setAdresse("Route de l’Aéroport Km 4 Sfax Tunisie");
			l.setCodePostal(3018);
			l.setEmailContact("contact@fsegs.rnu.tn");
			l.setTel(74278777);
			l.setFax(74279139);
			l.setVille(villeService.findByVille("sfax"));
			LieuEvService.ajoutLieu(l);
			demande.getLeslieu().add(l);
		}
		else
		{
			if(demande.getLeslieu().size()>0 || demande.getLeslieu()!=null)
			{
				//List<Lieu> checkLieu =LieuEvService.getAllLieu();
				for(Lieu site : demande.getLeslieu())
				{
					LieuEvService.ajoutLieu(site);
				}
  			}
		}
		
		DemandeEvenement de = new DemandeEvenement();		
		de=demandeEvServ.createDemandeEvent(demande);	
		if(demande.getLesSalles().size()>0 || demande.getLesSalles() !=null)
		{
			for(DemmandeSalle d : demande.getLesSalles())
			{
				for( Planification p : d.getLesplanification())
				{
				
					PlanifcationServ.addplanifications(p,d);
					
				}	
				d.setDemEvent(de);
				demandeSalleServ.affecter(d);
				
			}
					
		}
		if(demande.getLesMaterielAffecter().size()>0 || demande.getLesMaterielAffecter() !=null)
		{
			for(DemandeMaterielFac d : demande.getLesMaterielAffecter())
			{
				for( Planification p : d.getLesplanificationMateriel())
				{
				
					PlanifcationServ.addplanificationsMat(p,d);
					
				}	
				d.setDemEventMat(de);
				demandeMatFacServ.affecter(d);
				
			}
		}
		return de;
		
		
	}
	
	@PutMapping("/DemandeEvent/update")
	public DemandeEvenement updateDemandeEvent(@RequestBody DemandeEvenement demande){
		return demandeEvServ.updateDemandeEvent(demande);
	}
	
	@RequestMapping("/DemandeEvent/nonconfirmer")
	
	public List<NotifOnly> getNonConfirmed()
	{
		return demandeEvServ.getNonConfirmed();
	}
	
	@RequestMapping("/DemandeEvent/lessalles")
	public List<LesSalleAReserve> getDemSalle()
	{
		return demandeEvServ.getDemSalle();
	}
	
	@RequestMapping("/DemandeEvent/leMateriel")
	public List<LesMatARes> getDemMat()
	{
		return demandeEvServ.getDemMat();
	}
	@PutMapping("/DemandeEvent/Materiel/affectMat/{id}")
	public Planification affecterQteMateriel(@RequestBody Planification planif,@PathVariable Long id){
		Optional<DemandeMaterielFac> d = demandeMatFacServ.findDemandeMat(id);
		planif.setDemandeMatFac(d.get());
		return PlanifcationServ.affecterQteMateriel(planif);
	}
	@PutMapping("/DemandeEvent/Salle/affectSalle/{id}")
	public Planification affecterSalle(@RequestBody Planification planif,@PathVariable Long id){
		Optional<DemmandeSalle> d = demandeSalleServ.findbyid(id);
		planif.setDemandeSalle(d.get());
		return PlanifcationServ.affecterQteMateriel(planif);
	}
	
	/*
		/*ListIterator matAffectIt = demande.getLesMaterielAffecter().listIterator();
while(matAffectIt.hasNext()) {
DemandeMaterielFac demandeMatFac = (DemandeMaterielFac) matAffectIt.next(); 
ListIterator planifMat = demandeMatFac.getLesplanificationMateriel().listIterator();
while(planifMat.hasNext()) {
  Planification p = (Planification) planifMat.next();
  p.setDemandeMatFac(demandeMatFac);
  PlanifcationServ.addplanificationsMat(p);
}
DemandeEvenement lademandeEv = new DemandeEvenement();
lademandeEv.setId((long)6);
demandeMatFac.setDemEventMat(lademandeEv);
demandeMatFacServ.affecter(demandeMatFac);
}*/

	
	
}
