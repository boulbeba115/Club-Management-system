package com.GestiondesClub.dao;

import javax.transaction.Transactional;

import com.GestiondesClub.entities.Materiel;

@Transactional
public interface MaterielRepository extends MaterielBaseRepository<Materiel>{

}
