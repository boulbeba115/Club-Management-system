package com.GestiondesClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
    Utilisateur  findByMatricule(Long matricule);
    Utilisateur  findByLelogin(String login);

}
