package com.GestiondesClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.DemandeFinancement;

public interface LesDemFinanceRepository extends JpaRepository<DemandeFinancement, Long> {

}
