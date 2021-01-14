package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.AdminstrationRepo;
import com.GestiondesClub.dto.AdminDto;
import com.GestiondesClub.entities.Administration;

@Service
public class AdministrationService {
	@Autowired
	private AdminstrationRepo adminRepo;
	
	public Administration findbymat(Long id) {
		return adminRepo.findByMatricule(id);
	}

	public List<AdminDto> getAllAdmins() {
		return adminRepo.findAdminSecuredBy();
	}
}
