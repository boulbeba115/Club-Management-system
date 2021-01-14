package com.GestiondesClub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestiondesClub.dao.UtilisateurRepository;
import com.GestiondesClub.entities.Administration;
import com.GestiondesClub.entities.Utilisateur;

@Service
public class UtilisateurService {
	@Autowired
	private UtilisateurRepository userRepo;

	public List<Utilisateur> getAllUsers() {
		return userRepo.findAll();
	}
	
	public Utilisateur findbymat(Long id) {
		return userRepo.findByMatricule(id);
	}
	
	public boolean login(Utilisateur user) {
		Utilisateur u = new Utilisateur();
		u = userRepo.findByLelogin(user.getLelogin());
		if(user.getMotPass()==null||user.getLelogin()==null)
			return false;
		if(u.getLelogin().equals(user.getLelogin()) && u.getMotPass().equals(user.getMotPass()))
		{
			return true;
		}
		else
			return false;
		/*System.out.println("***********************************");
		System.out.println(etu.getCin() + "  " + e.getCin());
		System.out.println(etu.getMatricule() + "  " + e.getMatricule());
		System.out.println(" ***********************************");*/
	}

	public Utilisateur getUser(Utilisateur user) {
		return  userRepo.findByLelogin(user.getLelogin());
	}
	
	public Utilisateur findUserBylogin(String user) {
		return  userRepo.findByLelogin(user);
	}

	public void updateUsers(Utilisateur user) {
		userRepo.save(user);		
	}

	public  Optional<Utilisateur> findUserByid(long id) {
		return userRepo.findById(id);
	}

}
