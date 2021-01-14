package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.RoleEtudiant;
import com.GestiondesClub.dto.lesClubDeEtudiant;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.MembresDeClub;

public interface MembreDeClubRepository extends JpaRepository<MembresDeClub, Long>{

	List<RoleEtudiant> findByEtudiantLesReunionId(long idReunion);
	List<lesClubDeEtudiant> findByEtudiantId(long id);
	List<lesClubDeEtudiant> findByClub(Club c);
}
