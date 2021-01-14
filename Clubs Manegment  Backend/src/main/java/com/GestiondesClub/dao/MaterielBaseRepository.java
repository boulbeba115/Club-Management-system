package com.GestiondesClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.GestiondesClub.entities.Materiel;

@NoRepositoryBean
public interface MaterielBaseRepository <T extends Materiel> extends JpaRepository<T, Long> {
	

}
