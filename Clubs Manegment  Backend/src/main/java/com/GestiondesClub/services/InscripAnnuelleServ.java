package com.GestiondesClub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.InscriptionAnnuelleRepository;
import com.GestiondesClub.dto.InscriptionsClubs;
import com.GestiondesClub.dto.LesInscritsClub;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.InscriptionsAnnuelle;

@Service
public class InscripAnnuelleServ {

	@Autowired
	private InscriptionAnnuelleRepository inscripClubServ ;

	public void save(InscriptionsAnnuelle inscrit) {
		inscripClubServ.save(inscrit);
	}

	public List<LesInscritsClub> getAllInscription(Club c,AnneeUniversitaire au) {
		return inscripClubServ.findByAnneUniveAndMembresdeClubClub(au,c);
	}

	public InscriptionsAnnuelle updateEtatInscript(InscriptionsAnnuelle insAnn) {
		return inscripClubServ.save(insAnn);
	}

	public List<LesInscritsClub> getHistoryInscription(Club c) {
		return inscripClubServ.findByMembresdeClubClub(c);
	}

	public List<InscriptionsClubs> getEtudiantInscriptions(Long id) {
		return inscripClubServ.findByMembresdeClubEtudiantId(id);
	}
}
