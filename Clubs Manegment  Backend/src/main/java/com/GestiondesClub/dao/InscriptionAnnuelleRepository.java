package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.InscriptionsClubs;
import com.GestiondesClub.dto.LesInscritsClub;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Etudiant;
import com.GestiondesClub.entities.InscriptionsAnnuelle;

public interface InscriptionAnnuelleRepository extends JpaRepository<InscriptionsAnnuelle, Long> {

	List<LesInscritsClub> findByAnneUniveAndMembresdeClubClub(AnneeUniversitaire anneUniv,Club c);
	List<LesInscritsClub> findByMembresdeClubClub(Club c);
	List<InscriptionsClubs> findByMembresdeClubEtudiantId(long id);

}
 