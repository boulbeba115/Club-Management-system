package com.GestiondesClub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.LesDemFinanceRepository;
import com.GestiondesClub.entities.DemandeFinancement;

@Service
public class DemFinanceServ {
	@Autowired
	private LesDemFinanceRepository demFinanceRepo;

	public DemandeFinancement saveDemFin(DemandeFinancement df) {
		return demFinanceRepo.save(df);
	}
}
