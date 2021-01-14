package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.GestiondesClub.entities.DemmandeSalle;
import com.GestiondesClub.entities.Salle;

public interface DemandeSalleRepository  extends JpaRepository<DemmandeSalle, Long>{
	List<DemmandeSalle> findBysalle(Salle s);
}
