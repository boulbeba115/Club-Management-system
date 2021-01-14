package com.GestiondesClub.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestiondesClub.entities.ProgrammeEvent;

public interface ProgrammeEventDao extends JpaRepository<ProgrammeEvent, Long> {

	ProgrammeEvent findByLesActionId(long id);
}
