package com.GestiondesClub.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.EtudiantClubDao;
import com.GestiondesClub.dto.InscriptionsClubs;
import com.GestiondesClub.dto.LesInscritsClub;
import com.GestiondesClub.dto.lesClubDeEtudiant;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.ClubTarifInscription;
import com.GestiondesClub.entities.Etudiant;
import com.GestiondesClub.entities.InscriptionsAnnuelle;
import com.GestiondesClub.entities.MembresDeClub;
import com.GestiondesClub.entities.Utilisateur;
import com.GestiondesClub.services.AnneUnivService;
import com.GestiondesClub.services.ClubService;
import com.GestiondesClub.services.EtudiantServices;
import com.GestiondesClub.services.InscripAnnuelleServ;
import com.GestiondesClub.services.MembreClubServ;
import com.GestiondesClub.services.UtilisateurService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class InscriptionController {

	@Autowired
	private MembreClubServ mbrServ;
	@Autowired
	private InscripAnnuelleServ inscripClubServ;
	@Autowired 
	private UtilisateurService userServ;
	@Autowired
	private ClubService clubServ;
	@Autowired 
	private AnneUnivService anneUnivServ;
	@Autowired EtudiantServices etudiantServ;
	
	@PutMapping("/Inscription/Club")
	public void clubPayedInscrip(@RequestBody InscriptionsAnnuelle inscriptionClub)
	{	  
	  if(!(inscriptionClub.getMembresdeClub().getClub().getInscriptionOn()))
	  return ;
	  Utilisateur u =  userServ.findUserBylogin(inscriptionClub.getMembresdeClub().getEtudiant().getLelogin());
	  if(!(u instanceof Etudiant))
	  return ;
	  Etudiant e = (Etudiant)u;
	  MembresDeClub mdc= new MembresDeClub();
	  mdc.setClub(inscriptionClub.getMembresdeClub().getClub());
	  mdc.setEtudiant(e);
	  mdc.setRoleEtudiant("membre");
	  MembresDeClub newMembre = mbrServ.ajoutMembre(mdc);
	  InscriptionsAnnuelle inscrit = new InscriptionsAnnuelle();
	  inscrit.setAnneUnive(anneUnivServ.findlastOne());
	  inscrit.setMembresdeClub(newMembre);
	  inscrit.setInscripValid(false);
	  inscrit.setInscriptionConfirmation(false);
	  inscripClubServ.save(inscrit);
	}
	
	@PutMapping("/Inscription/Club/Free")
	public void clubFreeInscrip(@RequestBody InscriptionsAnnuelle inscriptionClub)
	{	  
	  if(!(inscriptionClub.getMembresdeClub().getClub().getInscriptionOn()))
	  return ;
	  Utilisateur u =  userServ.findUserBylogin(inscriptionClub.getMembresdeClub().getEtudiant().getLelogin());
	  if(!(u instanceof Etudiant))
	  return ;
	  Etudiant e = (Etudiant)u;
	  MembresDeClub mdc= new MembresDeClub();
	  mdc.setClub(inscriptionClub.getMembresdeClub().getClub());
	  mdc.setEtudiant(e);
	  mdc.setRoleEtudiant("membre");
	  MembresDeClub newMembre = mbrServ.ajoutMembre(mdc);
	  InscriptionsAnnuelle inscrit = new InscriptionsAnnuelle();
	  inscrit.setAnneUnive(anneUnivServ.findlastOne());
	  inscrit.setMembresdeClub(newMembre);
	  inscrit.setInscripValid(true);
	  inscrit.setInscriptionConfirmation(true);
	  inscripClubServ.save(inscrit);
	}
	@PutMapping("/Inscription/all/{id}")
	public List<LesInscritsClub>  getAllInscription(@PathVariable String id,@RequestBody AnneeUniversitaire anneeUvniv){
		AnneeUniversitaire au =anneUnivServ.findYearUniv(anneeUvniv.getAnneeDeb(),anneeUvniv.getAnneeFin()) ;
		Club c = clubServ.finClubByName(id);
		return inscripClubServ.getAllInscription(c,au);
	}
	
	@RequestMapping("/Inscription/history/{id}")
	public List<LesInscritsClub>  getHistoryInscription(@PathVariable String id){
		Club c = clubServ.finClubByName(id);
		return inscripClubServ.getHistoryInscription(c);
	}
	@RequestMapping("/Inscription/etudiant/{id}")
	public List<InscriptionsClubs>  getEtudiantInscriptions(@PathVariable String id){
		EtudiantClubDao e = etudiantServ.findByLog(id);
		return inscripClubServ.getEtudiantInscriptions(e.getId());
	}
	
	@RequestMapping("/AnneeUnive/List/{id}")
	public List<AnneeUniversitaire>  getUniverYears(@PathVariable String id){
		Club c = clubServ.finClubByName(id);
		Calendar cal = Calendar.getInstance();
		cal.setTime(c.getDateCreation());
		System.out.println(cal.get(Calendar.YEAR));
		return anneUnivServ.getUniverYears(cal.get(Calendar.YEAR));
	}
	@RequestMapping("/AnneeUnive/List/all")
	public List<AnneeUniversitaire>  getAllUniverYears(){
		return anneUnivServ.getAllUniverYears();
	}
	
	@PutMapping("/Inscription/etat/update")
	public boolean  etatInscription(@RequestBody InscriptionsAnnuelle insAnn){
		InscriptionsAnnuelle inscrip = inscripClubServ.updateEtatInscript(insAnn);
		if(inscrip!=null)
			return true;
		return false;
	}
	
	@GetMapping("club/logo/base64/{id}")
	public List<String> getClubBaseLogo(@PathVariable String id) throws IOException
	{
		List<String> logos= new ArrayList<String>();
		Club c = clubServ.finClubByName(id);
		String fseglogoPath="C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\fseglogo.png";
		byte[] fsegContent = FileUtils.readFileToByteArray(new File(fseglogoPath));
		String fsegencoding = Base64.getEncoder().encodeToString(fsegContent);
		logos.add(fsegencoding);

		String filepath="C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\ClubLogos\\";
		filepath+=c.getClubLogo();
		byte[] fileContent = FileUtils.readFileToByteArray(new File(filepath));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		logos.add(encodedString);
		System.out.println(logos.size());
		return logos;
	}
	
}
