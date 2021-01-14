package com.GestiondesClub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.entities.Inviter;
import com.GestiondesClub.services.InviterService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8000")
public class InviterController {
	@Autowired
	private InviterService invitServ;
	
	@RequestMapping("/inviter/byCin/{id}")
	public Inviter getInviterByCin(@PathVariable int id)
	{
		return invitServ.getInviterByCin(id);
	}
	
	@PutMapping("inviter/ajout")
	public Inviter saveInvited(@RequestBody  Inviter inviter)
	{
		return  invitServ.saveInvited(inviter);
	}
}
