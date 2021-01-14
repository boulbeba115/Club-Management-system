package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.RoleEtudiantDao;
import com.GestiondesClub.entities.RoleEtudiant;

@Service
public class RoleEtudiantServ {

	@Autowired
		private RoleEtudiantDao roleEtudiantDao;

	public List<RoleEtudiant> findRoles() {
		return roleEtudiantDao.findAll();
	}
	
}
