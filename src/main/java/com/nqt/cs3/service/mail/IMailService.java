package com.nqt.cs3.service.mail;

import java.io.IOException;
import java.util.List;

import com.nqt.cs3.domain.Course;

import jakarta.mail.MessagingException;

public interface IMailService {
    void sendMessageUsingThymeleafTemplate(String to, String subject, String name, List<Course> courseList) 
            throws IOException, MessagingException;
}
