package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.ClubTarifInscription;

public interface ClubTarifInscriptionRepository extends JpaRepository<ClubTarifInscription, Long>{
		
	ClubTarifInscription findByAnneeUniverAnneeDebAndAnneeUniverAnneeFinAndClubNomClub(int annDeb,int annFin,String nomClub);

}
