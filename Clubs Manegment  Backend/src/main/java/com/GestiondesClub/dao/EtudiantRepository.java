package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.EtudiantClubDao;
import com.GestiondesClub.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long>{
	EtudiantClubDao  findByMatricule(Long matricule);
    EtudiantClubDao  findByLelogin(String login);
	List<EtudiantClubDao> findEtudiantClubBy();

}
