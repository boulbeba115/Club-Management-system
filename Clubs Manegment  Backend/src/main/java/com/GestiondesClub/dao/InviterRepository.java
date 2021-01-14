package com.GestiondesClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.Inviter;

public interface InviterRepository extends JpaRepository<Inviter, Long>{
	Inviter findByCin(int cin);

}
