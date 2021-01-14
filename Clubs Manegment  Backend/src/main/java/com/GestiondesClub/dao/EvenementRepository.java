package com.GestiondesClub.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.DemandeFinacneAll;
import com.GestiondesClub.dto.Evenementdto;
import com.GestiondesClub.dto.EventAllAffiche;
import com.GestiondesClub.dto.eventProgDto;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {

		List<Evenementdto> findAllEventBy();
		Evenementdto findEventById(long id);
		List<Evenementdto> findByDemandeEvenementLeClub(Club c);
		eventProgDto findEventById(Long id);
		List<EventAllAffiche> findAllAfficheBy();
		EventAllAffiche findByLesDemandeAfficheId(long id);
		List<DemandeFinacneAll> findAllDemandeFinanceBy();
		List<Evenementdto> findByDemandeEvenementDatePrevuEventAfterAndPublication(Date d,boolean b);
		
		}
