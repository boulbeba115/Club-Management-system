package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.TypeEventRepository;
import com.GestiondesClub.entities.TypeClub;
import com.GestiondesClub.entities.TypeEvenement;

@Service
public class TypeEventServices {
	@Autowired
	private TypeEventRepository typeEv;

	public List<TypeEvenement> getAllType() {
		return typeEv.findAll();
	}

	public Optional<TypeEvenement> getType(Long id) {

		return typeEv.findById(id);
	}

	public TypeEvenement save(TypeEvenement type) {
		return typeEv.save(type);
	}

	public TypeEvenement updateType(TypeEvenement type) {
		return typeEv.save(type);

	}
	
	public boolean delete(long id) {
		typeEv.deleteById(id);
		return true;
	}

	
	
	

}
