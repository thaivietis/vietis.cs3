package com.nqt.cs3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nqt.cs3.service.MailService;

import jakarta.mail.MessagingException;

@Controller
public class EmailController {
    @Autowired
    private MailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() throws MessagingException {
        String to = "levanluan0109@gmail.com";
        String subject = "Thông báo từ hệ thống";
        emailService.sendMessageUsingThymeleafTemplate(to, subject);
        return "Email sent successfully!";
    }
}
