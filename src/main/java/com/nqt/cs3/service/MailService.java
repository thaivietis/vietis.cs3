package com.nqt.cs3.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.nqt.cs3.service.IService.IMailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("IMailService")
public class MailService implements IMailService {
    private static final String NOREPLY_ADDRESS = "quangthai170402@gmail.com";

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject) throws MessagingException {
        Context thymeleafContext = new Context();
        String htmlBody = thymeleafTemplateEngine.process("/mail/form-email.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }
}
