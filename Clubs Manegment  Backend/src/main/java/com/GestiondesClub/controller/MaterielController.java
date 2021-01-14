package com.GestiondesClub.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.GestiondesClub.dto.PlanificationMaterielDto;
import com.GestiondesClub.entities.Materiel;
import com.GestiondesClub.entities.MaterielFaculter;
import com.GestiondesClub.entities.Planification;
import com.GestiondesClub.entities.TypeEvenement;
import com.GestiondesClub.services.MaterialService;
import com.GestiondesClub.services.PlanificationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class MaterielController {
	private  static int qteRes=0;
	@Autowired
	private MaterialService materiaLServ;
	@Autowired
	private PlanificationService planifMatServ;
	
	@RequestMapping("/MaterielFaculter/list")
	public List<MaterielFaculter> getAllMatFac()
	{
		return materiaLServ.getAllMatFac();
	}
	@RequestMapping("/MaterielFaculter/{id}")
	public Optional<MaterielFaculter> getMatFac(@PathVariable Long id)
	{
		return materiaLServ.getMatFac(id);
	}
	
	@PutMapping("/MaterielFaculter/create")
	public MaterielFaculter createMatFac(@RequestBody MaterielFaculter matfac){
	
		return materiaLServ.save(matfac);	
	}
	

	@RequestMapping("/MaterielFaculter/QteDisp/{id}")
	public int getQteDisp(@PathVariable String id, @RequestBody MaterielFaculter matfac ) throws ParseException 
	{	qteRes=0;
		java.util.Date date =new SimpleDateFormat("yyyy-MM-dd").parse(id);  
		if(date==null)
		return 0;
		List<Planification> lesPlanifMat =  planifMatServ.getQteDisp(date,matfac);
		lesPlanifMat.forEach(planif->qteRes+=planif.getQteAffecter());
		return qteRes;
	}
	
}
