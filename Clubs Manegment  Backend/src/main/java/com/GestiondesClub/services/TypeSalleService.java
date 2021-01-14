package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.TypeSalleRepository;
import com.GestiondesClub.entities.TypeSalle;

@Service
public class TypeSalleService {
	@Autowired 
	private TypeSalleRepository typeSalleRepo;

	public List<TypeSalle> GetAllTypeSalle() {
		return typeSalleRepo.findAll();
	}

	public Optional<TypeSalle> getTypeSalle(Long id) {
		return typeSalleRepo.findById(id);
	}

	public TypeSalle createTypeSalle(TypeSalle typesalle) {
		return typeSalleRepo.save(typesalle);
	}

	public TypeSalle updateTypeSalle(TypeSalle typesalle) {
		return typeSalleRepo.save(typesalle);

	}
	
	
	
}
