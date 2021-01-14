package com.GestiondesClub.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.Salle;

public interface SalleRepository extends JpaRepository<Salle, Long> {

	List<Salle> findByLesDemandeLesplanificationDateResSalleOrLesReunionDateReunion(Date d1, Date d2 );
	

}
