package com.btgpactual.technicaltest.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.btgpactual.technicaltest.services.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
    @Value("${spring.mail.username}")
    private String fromEmail;
    
	@Override
    public void sendEmail(String to, String subject, String body) {
		
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);

        mailSender.send(message);
    };

};