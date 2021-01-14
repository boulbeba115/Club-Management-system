package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.ClubRepository;
import com.GestiondesClub.dto.ClubMembreDto;
import com.GestiondesClub.dto.Clubdto;
import com.GestiondesClub.dto.OnlyClubDto;
import com.GestiondesClub.entities.AnneeUniversitaire;
import com.GestiondesClub.entities.Club;


@Service
public class ClubService {
	@Autowired
	private ClubRepository clubRepo;

	public  List<Club> getAllClubs() {
		return (List<Club>) clubRepo.findAll();
	}
	
	public Optional<Club> getClub(Long id)
	{
		return clubRepo.findById(id);
	}

	public Club save(Club club) {
		return clubRepo.save(club);
	}

	public Club updateClub(Club club) {
		return clubRepo.save(club);
	}

	public ClubMembreDto getByName(String id) {
		return clubRepo.findByNomClub(id);
	}

	public List<Clubdto> getAllClub() {
		return clubRepo.findAllProjectedBy();
	}

	public List<ClubMembreDto> findAllClubWithMember() {
		return clubRepo.findClubMembreBy();
	}

	public ClubMembreDto findClubByYear(AnneeUniversitaire anneUnv, String id) {
		return clubRepo.findByTarifInscriptionAnneeUniverAndNomClub(anneUnv, id);
	}

	public List<OnlyClubDto> getOnlyClub() {
		return clubRepo.findAllClubBy();
	}
	public List<OnlyClubDto> getEtudiantClub(long id) {
		return clubRepo.findByLesEtudiantEtudiantId(id);
	}

	public Club finClubByName(String id) {
		return clubRepo.findClubByNomClub(id);
	}
	

}
