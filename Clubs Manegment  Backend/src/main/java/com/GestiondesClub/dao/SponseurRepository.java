package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Sponseur;

public interface SponseurRepository extends JpaRepository<Sponseur, Long>{
	List<Sponseur> findAllActivaSponByActiveTrue();
	List<Sponseur> findByLesEvenementEvenementDemandeEvenementLeClub(Club c);

}
