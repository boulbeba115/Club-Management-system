package com.GestiondesClub.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.GestiondesClub.entities.Mail;


@Service
public class EmailService {
	@Autowired
    private JavaMailSender emailSender;

public static int noOfQuickServiceThreads = 20;
	private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads); // Creates a thread pool that reuses fixed number of threads(as specified by noOfThreads in this case).
	
	public void sendASynchronousMail(final Mail mail) throws MailException,RuntimeException{
		 SimpleMailMessage message = new SimpleMailMessage();
	        message.setSubject(mail.getSubject());
	        message.setText(mail.getContent());
	        message.setTo(mail.getTo());
	        message.setFrom(mail.getFrom());
		quickService.submit(new Runnable() {
			@Override
			public void run() {
				try{
					emailSender.send(message);
				}catch(Exception e){
				}
			}
		});
	}
	
    public void sendSimpleMessage(final Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());
        emailSender.send(message);
    }
    

}
