package com.btgpactual.technicaltest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btgpactual.technicaltest.dto.EmailRequest;
import com.btgpactual.technicaltest.services.IEmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
	
    @Autowired
    private IEmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return "Email sent successfully";
    };

};