package com.GestiondesClub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.dto.DemandeFinacneAll;
import com.GestiondesClub.dto.Evenementdto;
import com.GestiondesClub.dto.EventAllAffiche;
import com.GestiondesClub.dto.eventProgDto;
import com.GestiondesClub.entities.Action;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.entities.DemandeEvenement;
import com.GestiondesClub.entities.Evenement;
import com.GestiondesClub.entities.EvenementSponseur;
import com.GestiondesClub.entities.ProgrammeEvent;
import com.GestiondesClub.entities.SponsarisationMateriel;
import com.GestiondesClub.services.ActionService;
import com.GestiondesClub.services.ClubService;
import com.GestiondesClub.services.DemEventServices;
import com.GestiondesClub.services.EvenementService;
import com.GestiondesClub.services.ProgrammeEventServ;
import com.GestiondesClub.services.SponsarisationMaterielleServ;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class EvenementController {
	@Autowired 
	private EvenementService eventServ;
	@Autowired
	private ClubService clubServ;
	@Autowired 
	private DemEventServices demEvServ;
	@Autowired
	private ProgrammeEventServ ProgEventServ;
	@Autowired
	private ActionService actionServs;
	@Autowired
	private EvenementSponseurServ eventSponServ;
	@Autowired
	private SponsarisationMaterielleServ sponMatServ;
	@RequestMapping("/event/All/list")
	public List<Evenementdto> getAllEvents()
	{
		return eventServ.getAllEvent();
	}
	
	@RequestMapping("/event/ByClub/list/{id}")
	public List<Evenementdto> getEventsByClub(@PathVariable String id)
	{	
		ClubMembreDto c = clubServ.getByName(id);
		if(c==null)
			return null;
		Club club = new Club();
		club.setId(c.getId());
		club.setNomClub(c.getNomClub());
		return eventServ.getEventsByClub(club);
	}
	
	@RequestMapping("/event/{id}")
	public Optional<Evenement> getClub(@PathVariable Long id)
	{
		return eventServ.getEvenement(id);
	}
	
	@PutMapping("/event/create")
	public Evenement createClub(@RequestBody Evenement event){
		DemandeEvenement m = event.getDemandeEvenement();
		m.setApprouvEvent(true);
		demEvServ.updateDemandeEvent(m);
		return eventServ.save(event);	
	}
	@PutMapping("/event/Programe/Affect/{id}")
	public List<ProgrammeEvent> affectProgrammme(@PathVariable long id , @RequestBody ProgrammeEvent prog){
		if(prog==null) {
			return null;
		}
		Evenement e = eventServ.getEvenement(id).get();
		e.getLesProgrammes().add(prog);
		eventServ.save(e);
		return e.getLesProgrammes();
	}
	@PutMapping("/event/Prog/action/create")
	public void ajoutAction(@RequestBody ProgrammeEvent progEvent){
		ProgEventServ.SaveProg(progEvent);
	}
	@PutMapping("/event/Prog/action/update")
	public void ajoutAction(@RequestBody Action action){
		System.out.println(action);
		/*
		actionServs.updateAction(action);*/
		ProgrammeEvent e = ProgEventServ.findProgAction(action.getId());
		if(e==null || (e.getLesAction().size()==0))
			return;
		int index = e.getLesAction().indexOf(action);
		e.getLesAction().get(index).setAction(action.getAction());
		e.getLesAction().get(index).setTempDebAct(action.getTempDebAct());
		e.getLesAction().get(index).setTempFinAct(action.getTempFinAct());
		ProgEventServ.update(e);
	}
	@DeleteMapping("/event/Prog/action/{id}")
	public void deleteAction(@PathVariable long id) {
		ProgrammeEvent e = ProgEventServ.findProgAction(id);
		if(e==null || (e.getLesAction().size()==0))
			return;
		Action a = new Action();
		a.setId(id);
		int index = e.getLesAction().indexOf(a);
		e.getLesAction().remove(index);
		ProgEventServ.update(e);
	}
	
	@PutMapping("/demandeAffiche/cree/{id}")
	public List<DemandeAffiche> saveDemandeAffiche(@PathVariable Long id , @RequestBody DemandeAffiche demAff)
	{		
		if(demAff.getDemandeEcrite()==null||demAff.getDemandeImage()==null)
			return null ;
		 Optional<Evenement> e = eventServ.getEvenement(id);
		 if(e==null)
			 return null;
         Evenement event =e.get();
         event.getLesDemandeAffiche().add(demAff);
 		return eventServ.saveDemAffiche(event);
	}
	
	@RequestMapping("demandeAffiche/list")
	public List<EventAllAffiche> getAllDemAffiche()
	{	
		return eventServ.FindAllDemAff();
	}	
	@RequestMapping("demandeFinance/list")
	public List<DemandeFinacneAll> getAllDemFinance()
	{	
		return eventServ.getAllDemFinance();
	}	
	@PutMapping("/affiche/delete/{id}")
	public void SuppAffichg (@PathVariable long id) {
		EventAllAffiche e = eventServ.findAfficheEvent(id);
		if(e==null || (e.getLesDemandeAffiche().size()==0))
			return;
		Evenement event = new Evenement();
		event.setId(e.getId());
		event.setLesDemandeAffiche(e.getLesDemandeAffiche());
		DemandeEvenement demEvent = new DemandeEvenement();
		demEvent.setId(e.getDemandeEvenement().getId());
		event.setDemandeEvenement(demEvent);
		event.setLesProgrammes(e.getLesProgrammes());
		event.setLesSponseur(e.getLesSponseur());
		DemandeAffiche da = new DemandeAffiche();
		da.setId(id);
		int index = event.getLesDemandeAffiche().indexOf(da);
		if(index<0)
			return;
		event.getLesDemandeAffiche().remove(index);
		System.out.println(event);
		eventServ.save(event);
	}
	@PutMapping("/event/AddSponseur/{id}")
	public void AjoutSponsarisation(@PathVariable long id ,@RequestBody EvenementSponseur eventSpon){
		Evenement event = new Evenement();
		event.setId(id);
		EvenementSponseur es = new EvenementSponseur();
		es.setEvenement(event);
		es.setSponseur(eventSpon.getSponseur());
		es.setMontant(eventSpon.getMontant());
		es.setLesMaterielSpon(eventSpon.getLesMaterielSpon());
		es.setValeurTotale(eventSpon.getValeurTotale());
		EvenementSponseur eventSpons = eventSponServ.AddSponsorisation(es);
		SponsarisationMateriel sponMat = new SponsarisationMateriel();
		if(eventSpon.getLesMaterielSpon().size()>0)
		{
			for(SponsarisationMateriel sm: eventSpon.getLesMaterielSpon())
			{
				sponMat=sm;
				sponMat.setEvenementSpon(eventSpons);
				sponMatServ.saveSponMat(sponMat);		
			}
		}

	}
	@PutMapping("/event/publier")
	public boolean publierEvent(@RequestBody Evenement event){
		Evenement e = eventServ.findById(event.getId()).get();
		e.setPublication(true);
		Evenement ev =  eventServ.save(e);
		if(ev == null)
		return false;
		return true;
	}
	@GetMapping("/event/By/{id}")
	public Evenementdto findEventById(@PathVariable long id ) {
	return eventServ.findEvenementById(id);
	}

}
