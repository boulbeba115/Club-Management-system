package com.GestiondesClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.dto.DemEventDto;
import com.GestiondesClub.dto.LesMatARes;
import com.GestiondesClub.dto.LesSalleAReserve;
import com.GestiondesClub.dto.NotifOnly;
import com.GestiondesClub.entities.Club;
import com.GestiondesClub.entities.DemandeEvenement;

public interface DemandeEventRepository extends JpaRepository<DemandeEvenement, Long> {
	@Query
	 List<NotifOnly> findByConfirmerEventFalse();
	
	List<DemEventDto> findByLeClub(Club c);
	
	List<DemEventDto> findDemEventBy();
	/*@Query("SELECT de FROM DemandeEvenement de join DemmandeSalle ds on de.id = ds.demEvent.id "
		  + "where de.datePrevuEvent >= DATE_FORMAT(SYSDATE(), '%Y-%m-%d')")
	@Query("SELECT de FROM DemandeEvenement de join DemmandeSalle ds on de.id = ds.demEvent.id "
			  + "where de.accepterEvent = true")*/
	List<LesSalleAReserve> findByAccepterEventTrue();
	
	@Query("SELECT de FROM DemandeEvenement de where de.accepterEvent = true")
     List<LesMatARes> findByMaterielDemander();

}
