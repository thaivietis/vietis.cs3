package com.nqt.cs3.service.IService;

import java.io.IOException;

import jakarta.mail.MessagingException;

public interface IMailService {
    void sendMessageUsingThymeleafTemplate(String to, String subject) 
            throws IOException, MessagingException;
}
