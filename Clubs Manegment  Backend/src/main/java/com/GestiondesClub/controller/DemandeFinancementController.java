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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.entities.DemandeFinancement;
import com.GestiondesClub.entities.Evenement;
import com.GestiondesClub.entities.LigneDevi;
import com.GestiondesClub.services.DemFinanceServ;
import com.GestiondesClub.services.EvenementService;
import com.GestiondesClub.services.LigneDeviService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class DemandeFinancementController {

	@Autowired
	private DemFinanceServ demFinanceServ;
	@Autowired
	private EvenementService eventServ;
	@Autowired
	private LigneDeviService ligneDeviServ;
	@RequestMapping(value="DemandeFinance/Devi", method=RequestMethod.POST, consumes = { "multipart/form-data" })
	public String uploadFile(@RequestParam("file") MultipartFile file ) throws IOException {
		String imgname = UUID.randomUUID().toString()+'-'+file.getOriginalFilename();
		File convertFile = new File("C:\\Users\\boulbeba\\Desktop\\front end gestion\\static\\img\\devi\\"+imgname);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return imgname;
	}
	@PutMapping("DemandeFinance/Add/{id}")
	public DemandeFinancement ajoutDemFinance(@PathVariable long id ,@RequestBody DemandeFinancement df) {
		 System.out.println(df.getDeviMateriel().size());
		List<LigneDevi> listLd = new ArrayList<LigneDevi>();
		 listLd=df.getDeviMateriel();
		 DemandeFinancement demFin = demFinanceServ.saveDemFin(df);
		 for(LigneDevi l : listLd) {
			 LigneDevi ld = new LigneDevi();
			 ld.setMatExterne(l.getMatExterne());
			 ld.setMontant(l.getMontant());
			 ld.setQuantite(l.getQuantite());
			 ld.setDemandeFinance(demFin);
			 ligneDeviServ.addligndev(ld);
		 }
		 Evenement e = eventServ.findById(id).get();
		 e.getLesDemandeFinance().add(demFin);
		 eventServ.save(e);
		 return demFin;
	}
	
	@PutMapping("/demandeFinance/Confirme")
	public void confirmerFinancement(@RequestBody DemandeFinancement df)
	{		
		df.setEtat(true);
		demFinanceServ.saveDemFin(df);
	}
	
	
}
