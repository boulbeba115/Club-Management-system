package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.TypeClubRepository;
import com.GestiondesClub.entities.TypeClub;

@Service
public class TypeClubService {
	@Autowired
	private  TypeClubRepository typeRepo;

	public List<TypeClub> getAllType() {
		return typeRepo.findAll();
	}

	public Optional<TypeClub> getType(Long id) {
		return typeRepo.findById(id);
	}

	public TypeClub save(TypeClub type) {
		return typeRepo.save(type);
	}

	public TypeClub updateClub(TypeClub type) {
		return typeRepo.save(type);
	}

	public boolean delete(long id) {
		typeRepo.deleteById(id);
		return true;
	}
	

}
