package com.GestiondesClub.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="programme_event")
@NamedQuery(name="ProgrammeEvent.findAll", query="SELECT pe FROM ProgrammeEvent pe")
public class ProgrammeEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(unique = true, nullable = false)
	private java.util.Date dateProgramme;
	 
	@Basic
	@Temporal(TemporalType.TIME)
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private java.util.Date tempDebProg;
	
	@Basic
	@Temporal(TemporalType.TIME)
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private java.util.Date tempFinProg;
	
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Action> lesAction;
	
	public ProgrammeEvent() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDateProgramme() {
		return dateProgramme;
	}

	public void setDateProgramme(java.util.Date dateProgramme) {
		this.dateProgramme = dateProgramme;
	}

	public java.util.Date getTempDebProg() {
		return tempDebProg;
	}

	public void setTempDebProg(java.util.Date tempDebProg) {
		this.tempDebProg = tempDebProg;
	}

	public java.util.Date getTempFinProg() {
		return tempFinProg;
	}

	public void setTempFinProg(java.util.Date tempFinProg) {
		this.tempFinProg = tempFinProg;
	}

	public List<Action> getLesAction() {
		return lesAction;
	}

	public void setLesAction(List<Action> lesAction) {
		this.lesAction = lesAction;
	}

	@Override
	public String toString() {
		return "ProgrammeEvent [id=" + id + ", dateProgramme=" + dateProgramme + ", tempDebProg=" + tempDebProg
				+ ", tempFinProg=" + tempFinProg + ", lesAction=" + lesAction + "]";
	}
	
	 
	
}
