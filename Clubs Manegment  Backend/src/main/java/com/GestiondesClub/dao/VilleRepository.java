package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.Ville;

public interface VilleRepository extends JpaRepository<Ville, Long>{
	public Ville findByVille(String ville);


}
