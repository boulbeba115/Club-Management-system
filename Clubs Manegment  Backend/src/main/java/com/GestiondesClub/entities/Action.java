package com.GestiondesClub.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="action")
@NamedQuery(name="Action.findAll", query="SELECT a FROM Action a")
public class Action {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private String action;
	
	@Basic
	@Temporal(TemporalType.TIME)
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private java.util.Date tempDebAct;
	
	@Basic
	@Temporal(TemporalType.TIME)
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private java.util.Date tempFinAct;

	public Action() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	

	public java.util.Date getTempDebAct() {
		return tempDebAct;
	}

	public void setTempDebAct(java.util.Date tempDebAct) {
		this.tempDebAct = tempDebAct;
	}

	public java.util.Date getTempFinAct() {
		return tempFinAct;
	}

	public void setTempFinAct(java.util.Date tempFinAct) {
		this.tempFinAct = tempFinAct;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Action))
			return false;
		Action other = (Action) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Action [id=" + id + ", action=" + action + ", tempDebAct=" + tempDebAct + ", tempFinAct=" + tempFinAct
				+ "]";
	}

	


	
	
	
	
}
