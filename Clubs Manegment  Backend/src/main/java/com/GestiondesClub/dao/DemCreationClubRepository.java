package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.CreeClubDto;
import com.GestiondesClub.entities.DemandeCreationClub;

public interface DemCreationClubRepository extends JpaRepository<DemandeCreationClub, Long>{
	List<CreeClubDto> findAllProjectedBy();
	CreeClubDto findByNomClub(String nomClub);

}
