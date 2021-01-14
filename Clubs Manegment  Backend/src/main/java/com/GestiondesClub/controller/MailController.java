package com.GestiondesClub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestiondesClub.entities.Mail;
import com.GestiondesClub.services.EmailService;



@RestController
public class MailController {

	@Autowired
    private EmailService emailService;

	@RequestMapping("Mail/envoyer")
	public String hello()
	{
		 Mail mail = new Mail();
	    mail.setFrom("systemgestionclubfsegs@gmail.com");
	    mail.setTo("boulbebazz@yahoo.fr");
	    mail.setSubject("test");
	    mail.setContent("test");
	    emailService.sendSimpleMessage(mail);
	    return "ok";
	}
	
}
