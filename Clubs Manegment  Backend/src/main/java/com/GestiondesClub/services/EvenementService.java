package com.GestiondesClub.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.EvenementRepository;
import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.dto.DemandeFinacneAll;
import com.GestiondesClub.dto.Evenementdto;
import com.GestiondesClub.dto.EventAllAffiche;
import com.GestiondesClub.dto.eventProgDto;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeAffiche;
import com.GestiondesClub.entities.Evenement;
import com.GestiondesClub.entities.ProgrammeEvent;

@Service
public class EvenementService {
	@Autowired
	private EvenementRepository eventRespo;

	public List<Evenementdto> getAllEvent() {
		return eventRespo.findAllEventBy();
	}

	public Optional<Evenement> getEvenement(Long id) {
		return eventRespo.findById(id);
	}

	public Evenement save(Evenement event) {
		return eventRespo.save(event);
	}

	public List<Evenementdto> getEventsByClub(Club club) {
		return eventRespo.findByDemandeEvenementLeClub(club);
	}

	public eventProgDto findEventById(Long id) {
		return eventRespo.findEventById(id);
	}

	public List<EventAllAffiche> FindAllDemAff() {
		return eventRespo.findAllAfficheBy();
	}

	public  EventAllAffiche findAfficheEvent(long id) {
		return eventRespo.findByLesDemandeAfficheId(id);
	}
	public List<DemandeAffiche> saveDemAffiche(Evenement event) {
		Evenement e = eventRespo.save(event);
		return e.getLesDemandeAffiche();
	}

	public Optional<Evenement> findById(long id) {
		return eventRespo.findById(id);
	}

	public List<DemandeFinacneAll> getAllDemFinance() {
		return eventRespo.findAllDemandeFinanceBy();
	}

	public List<Evenementdto> getAllNotExp() {
		return eventRespo.findByDemandeEvenementDatePrevuEventAfterAndPublication(new Date(),true);
	}

	public Evenementdto findEvenementById(long id) {
		return eventRespo.findEventById(id);
	}
	
}
