package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.dto.Clubdto;
import com.GestiondesClub.dto.OnlyClubDto;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.Etudiant;

public interface ClubRepository extends JpaRepository<Club, Long> {

		ClubMembreDto findByNomClub(String nomclub);
		List<Clubdto> findAllProjectedBy();
		List<ClubMembreDto> findClubMembreBy();
		ClubMembreDto findByTarifInscriptionAnneeUniverAndNomClub(AnneeUniversitaire annUniv,String nomClub);
		Clubdto findById(long id);
		List<OnlyClubDto> findAllClubBy();
		List<OnlyClubDto> findByLesEtudiantEtudiantId(long id);
		Club findClubByNomClub(String nomlub);
		Club findClubByNomClubAndLesEtudiantRoleEtudiantIsNotLike(String nomlub,String role);

}
