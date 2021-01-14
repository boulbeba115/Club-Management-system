package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.DemandeEventRepository;
import com.GestiondesClub.dto.DemEventDto;
import com.GestiondesClub.dto.LesMatARes;
import com.GestiondesClub.dto.LesSalleAReserve;
import com.GestiondesClub.dto.NotifOnly;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeEvenement;

@Service
public class DemEventServices {
	@Autowired
		private DemandeEventRepository demandeEv;

	public List<DemEventDto> GetAllDemande() {
		return demandeEv.findDemEventBy();
	}

	public Optional<DemandeEvenement> getDemandeEvent(Long id) {
		return demandeEv.findById(id);
	}

	public DemandeEvenement createDemandeEvent(DemandeEvenement demande) {
		return demandeEv.save(demande);
		
	}

	public DemandeEvenement updateDemandeEvent(DemandeEvenement demande) {
		return demandeEv.save(demande);

	}

	public List<NotifOnly> getNonConfirmed() {
		return demandeEv.findByConfirmerEventFalse();
	}

	public List<DemEventDto> getAllByclub(Club c) {
		return demandeEv.findByLeClub(c);
	}

	public List<LesSalleAReserve> getDemSalle() {
		return demandeEv.findByAccepterEventTrue();
	}

	public List<LesMatARes> getDemMat() {
		return demandeEv.findByMaterielDemander();

	}

	
	

}
