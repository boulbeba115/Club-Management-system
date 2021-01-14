package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.AnneeUniversitaire;

public interface AnneUnivRepository extends JpaRepository<AnneeUniversitaire, Long>{

	AnneeUniversitaire findByAnneeDebAndAnneeFin(int AnneeDeb,int AnneeFin);
	List<AnneeUniversitaire> findByAnneeFinOrAnneeDeb(int AnneeFin,int AnneeDeb);
	AnneeUniversitaire findFirstByOrderByIdDesc();
}
