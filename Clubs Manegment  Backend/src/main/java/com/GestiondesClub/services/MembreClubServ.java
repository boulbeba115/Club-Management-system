package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.MembreDeClubRepository;
import com.GestiondesClub.dto.RoleEtudiant;
import com.GestiondesClub.dto.lesClubDeEtudiant;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.MembreFondateur;
import com.GestiondesClub.entities.MembresDeClub;
import com.GestiondesClub.entities.Reunion;


@Service
public class MembreClubServ {
	@Autowired
		private MembreDeClubRepository membreClubRepo;

	public void saveMembre(MembreFondateur membreFondateur , Club c) {
		
		MembresDeClub mc = new MembresDeClub();
		mc.setClub(c);
		mc.setEtudiant(membreFondateur.getEtudiant());
		mc.setRoleEtudiant(membreFondateur.getRoleEtudiant().getRole());
		membreClubRepo.save(mc);
	}

	public List<RoleEtudiant> getEtudiantListByClub(long id) {
		return membreClubRepo.findByEtudiantLesReunionId(id);
	}

	public MembresDeClub ajoutMembre(MembresDeClub mdc) {
		return membreClubRepo.save(mdc);
	}


}
