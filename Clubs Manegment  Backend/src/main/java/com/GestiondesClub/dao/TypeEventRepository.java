package com.GestiondesClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.TypeEvenement;

public interface TypeEventRepository extends JpaRepository<TypeEvenement, Long> {


}
