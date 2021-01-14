package com.GestiondesClub.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.PlanificationMaterielDto;
import com.GestiondesClub.dto.PlanificationSalleDto;
import com.GestiondesClub.entities.MaterielFaculter;
import com.GestiondesClub.entities.Planification;

public interface PlanificationRepsitory extends JpaRepository<Planification, Long>{
	
	List<PlanificationSalleDto> findByDateResSalleAndAcceptationTrue(Date date);
	List<PlanificationMaterielDto> findLeByDateResSalleAndTypePlanification(Date date,String typePlanification);
	List<Planification> findByDateResSalleAndDemandeMatFacMatFaculter(Date d ,MaterielFaculter m);

	
}
