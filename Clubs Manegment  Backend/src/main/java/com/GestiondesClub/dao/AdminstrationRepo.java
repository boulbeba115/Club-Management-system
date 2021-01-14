package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.AdminDto;
import com.GestiondesClub.entities.Administration;

public interface AdminstrationRepo extends JpaRepository<Administration, Long>{
	Administration  findByMatricule(Long matricule);
	List<AdminDto> findAdminSecuredBy();

}
