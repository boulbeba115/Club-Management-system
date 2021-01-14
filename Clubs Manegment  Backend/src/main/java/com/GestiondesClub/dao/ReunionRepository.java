package com.GestiondesClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.GestiondesClub.dto.ReunionDto;
import com.GestiondesClub.entities.Reunion;


public interface ReunionRepository extends JpaRepository<Reunion, Long>{

	List<ReunionDto> findAllBy();
	List<ReunionDto> findByLeClubNomClub(String clubname);
}
