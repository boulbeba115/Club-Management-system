package com.GestiondesClub.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.dto.ReunionDto;
import com.GestiondesClub.entities.Inviter;
import com.GestiondesClub.entities.Mail;
import com.GestiondesClub.entities.ParticipantEtudiant;
import com.GestiondesClub.entities.Reunion;
import com.GestiondesClub.entities.ReunionInviter;
import com.GestiondesClub.entities.ReunionParticipant;
import com.GestiondesClub.entities.SponsarisationMateriel;
import com.GestiondesClub.entities.Utilisateur;
import com.GestiondesClub.services.EmailService;
import com.GestiondesClub.services.ParticipEtudiantServ;
import com.GestiondesClub.services.ReunionInviterService;
import com.GestiondesClub.services.ReunionParticipantService;
import com.GestiondesClub.services.ReunionServ;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class ReunionController {

	@Autowired
	private ReunionServ reunionServ;
	@Autowired
	private ReunionParticipantService reunionPartServ;
	@Autowired
	private ReunionInviterService reunionInvitServ;
	@Autowired
	private  ParticipEtudiantServ partEtudiantServ;
	@Autowired
	private EmailService mailServ;
	
	@RequestMapping("/reunion/list")
	public List<ReunionDto> gatAllReunion()
	{
		return reunionServ.gatAllReunion();
	}
	@RequestMapping("/reunion/club/{id}")
	public List<ReunionDto> gatAllReunion(@PathVariable String id)
	{
		return reunionServ.findByClubName(id);
	}
	
	@PutMapping("/reunion/presentMember/{id}")
	public void presentMember(@PathVariable long id , @RequestBody  ParticipantEtudiant pe)
	{
		ParticipantEtudiant partetu = new ParticipantEtudiant();
		Reunion r = new Reunion();
		r.setId(id);
		partetu.setId(pe.getId());
		partetu.setAbsent(pe.getAbsent());
		partetu.setReunion(r);
		partetu.setParticipantEtudiant(pe.getParticipantEtudiant());
		partEtudiantServ.presentMember(partetu);
	}
	
	@PutMapping("/reunion/Inviter/{id}")
	public void presentInviter(@PathVariable long id , @RequestBody  ReunionInviter pe)
	{
		ReunionInviter invited = new ReunionInviter();
		Reunion r = new Reunion();
		r.setId(id);
		invited.setId(pe.getId());
		invited.setAbsent(pe.getAbsent());
		invited.setReunion(r);
		invited.setInviter(pe.getInviter());
		reunionInvitServ.presentInviter(invited);
	}
	
	@PutMapping("/reunion/administration/{id}")
	public void presentAdmin(@PathVariable long id , @RequestBody  ReunionParticipant pe)
	{
		ReunionParticipant partadmin = new ReunionParticipant();
		Reunion r = new Reunion();
		r.setId(id);
		partadmin.setId(pe.getId());
		partadmin.setAbsent(pe.getAbsent());
		partadmin.setReunion(r);
		partadmin.setParticipantAdministration(pe.getParticipantAdministration());
		reunionPartServ.presentAdmin(partadmin);
	}
	
	@PutMapping("/reunion/cree")
	public void saveReunion(@RequestBody Reunion r)
	{
		Reunion reunion = reunionServ.saveReunion(r);
		ReunionParticipant leParticipant = new ReunionParticipant();
		ReunionInviter inviterReunion = new ReunionInviter();
		ParticipantEtudiant partEtudiant = new ParticipantEtudiant();
		if(r.getLesParticipantsAdmins().size()>0)
		{
			for(ReunionParticipant rp: r.getLesParticipantsAdmins())
			{	leParticipant=rp;
				leParticipant.setAbsent(null);
				leParticipant.setReunion(reunion);
				reunionPartServ.saveParticipantReunion(leParticipant);		
			}
		}
		if(r.getLesParticipantsetudiants().size()>0)
		{
			for(ParticipantEtudiant pe: r.getLesParticipantsetudiants())
			{	partEtudiant=pe;
				leParticipant.setAbsent(null);
				partEtudiant.setReunion(reunion);
				partEtudiantServ.saveEtudiantPart(partEtudiant);		
			}
		}
		if(r.getLesInviter().size()>0)
		{
			for(ReunionInviter ir: r.getLesInviter())
			{
				inviterReunion=ir;
				leParticipant.setAbsent(null);
				inviterReunion.setReunion(reunion);
				reunionInvitServ.saveReunionInviter(inviterReunion);		
			}
		}
		this.sendsInvitation(r);
		
	}
	public void sendsInvitation(Reunion r)
	{
		Mail mail = new Mail();
         Calendar c = Calendar.getInstance();
         c.setTime(r.getDateReunion());
         
	    LocalDate localDate = LocalDate.of(c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

		String dateReunion =localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy",Locale.FRENCH));
		
		SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
		System.out.println(c.get(Calendar.YEAR));
		
		mail.setContent("Vous avez eté invité aux réunions "+r.getTitre() +" le "+ dateReunion + " de "+ timeformat.format(r.getTempdebReunion())+" a "
		+timeformat.format(r.getTempfinReunion()) + " au salle "+r.getSalleReunion().getNomSalle()+" \n Cordialement, \n "+r.getLeClub().getNomClub());
		mail.setFrom("systemgestionclubfsegs@gmail.com");
		mail.setSubject(r.getSujet());		
		List<String> mailList = new ArrayList<String>() ;
		if(r.getLesInviter()!=null)
		r.getLesInviter().forEach(inviter -> mailList.add(inviter.getInviter().getEmail()));
		if(r.getLesParticipantsetudiants()!=null)
		r.getLesParticipantsetudiants().forEach(membre -> mailList.add(membre.getParticipantEtudiant().getEmail()));
		if(r.getLesParticipantsAdmins()!=null)
		r.getLesParticipantsAdmins().forEach(administration -> mailList.add(administration.getParticipantAdministration().getEmail()));
		
		mailList.forEach(email -> {
			mail.setTo(email);
			mailServ.sendASynchronousMail(mail);
			});
	}
}
