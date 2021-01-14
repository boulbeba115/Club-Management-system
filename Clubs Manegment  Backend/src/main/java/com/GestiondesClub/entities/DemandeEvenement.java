package com.GestiondesClub.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="demande_Evenement")
public class DemandeEvenement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="nomEvenement", unique = true, nullable = false)
	private String nomEvenement ;
	
	@Column(name="DescriptionEvenement")
	@Type(type="text")
	private String DescriptionEvenement ;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateCreation")
	private Date datedemande ;
	
	@Temporal(TemporalType.DATE)
	@Column(name="datePrevuEvent")
	private Date datePrevuEvent ;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateFinEvent")
	private Date dateFinEvent ;
	
	@Column(name="externe")
	private boolean externe;
	
	@Column(name="confirmerEvent")
	private boolean confirmerEvent;
	
	@Column(name="approuveevent")
	private boolean approuvEvent;
	
	@Column(name="accepterEvent")
	private boolean accepterEvent;
	
	@ManyToOne
	private TypeEvenement typeEvent;
	
	@ManyToOne
	private Club leClub;
	
	public DemandeEvenement() {
		super();
	}

    @ManyToMany
    @JoinTable(
    		  name = "lieus_evenement", 
    		  joinColumns = @JoinColumn(name = "demmandeEvent_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "lieu_id"))
    private List<Lieu> leslieu;

    @OneToMany(mappedBy = "demEvent")
    private List<DemmandeSalle> lesSalles;
    
    
    @OneToMany(mappedBy = "demEventMat")
    private List<DemandeMaterielFac> lesMaterielAffecter;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomEvenement() {
		return nomEvenement;
	}

	public void setNomEvenement(String nomEvenement) {
		this.nomEvenement = nomEvenement;
	}

	public String getDescriptionEvenement() {
		return DescriptionEvenement;
	}

	public void setDescriptionEvenement(String descriptionEvenement) {
		DescriptionEvenement = descriptionEvenement;
	}

	public Date getDatedemande() {
		return datedemande;
	}

	public void setDatedemande(Date datedemande) {
		this.datedemande = datedemande;
	}

	public Date getDatePrevuEvent() {
		return datePrevuEvent;
	}

	public void setDatePrevuEvent(Date datePrevuEvent) {
		this.datePrevuEvent = datePrevuEvent;
	}

	public Date getDateFinEvent() {
		return dateFinEvent;
	}

	public void setDateFinEvent(Date dateFinEvent) {
		this.dateFinEvent = dateFinEvent;
	}

	public boolean isExterne() {
		return externe;
	}

	public void setExterne(boolean externe) {
		this.externe = externe;
	}

	public TypeEvenement getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(TypeEvenement typeEvent) {
		this.typeEvent = typeEvent;
	}

	public List<Lieu> getLeslieu() {
		return leslieu;
	}

	public void setLeslieu(List<Lieu> leslieu) {
		this.leslieu = leslieu;
	}

	public List<DemmandeSalle> getLesSalles() {
		return lesSalles;
	}

	public void setLesSalles(List<DemmandeSalle> lesSalles) {
		this.lesSalles = lesSalles;
	}

	public List<DemandeMaterielFac> getLesMaterielAffecter() {
		return lesMaterielAffecter;
	}

	public void setLesMaterielAffecter(List<DemandeMaterielFac> lesMaterielAffecter) {
		this.lesMaterielAffecter = lesMaterielAffecter;
	}

	public boolean isConfirmerEvent() {
		return confirmerEvent;
	}

	public void setConfirmerEvent(boolean confirmerEvent) {
		this.confirmerEvent = confirmerEvent;
	}

	public boolean isAccepterEvent() {
		return accepterEvent;
	}

	public void setAccepterEvent(boolean accepterEvent) {
		this.accepterEvent = accepterEvent;
	}

	public Club getLeClub() {
		return leClub;
	}

	public void setLeClub(Club leClub) {
		this.leClub = leClub;
	}

	public boolean getApprouvEvent() {
		return approuvEvent;
	}

	public void setApprouvEvent(boolean approuvEvent) {
		this.approuvEvent = approuvEvent;
	} 
	
	
	
	

}
