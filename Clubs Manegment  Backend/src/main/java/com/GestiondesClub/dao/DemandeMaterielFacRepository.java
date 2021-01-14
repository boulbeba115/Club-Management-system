package com.GestiondesClub.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.DemandeEvenement;
import com.GestiondesClub.entities.DemandeMaterielFac;
import com.GestiondesClub.entities.MaterielFaculter;
import com.GestiondesClub.entities.Planification;

public interface DemandeMaterielFacRepository extends JpaRepository<DemandeMaterielFac, Long> {

	List<DemandeMaterielFac> findByMatFaculterAndDemEventMat(MaterielFaculter m , DemandeEvenement de);

	
}
