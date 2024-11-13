package com.nqt.cs3.component;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nqt.cs3.dto.EmailReaderItemDTO;
import com.nqt.cs3.service.MailService;

@Component
public class EmailItemProcess implements ItemProcessor<EmailReaderItemDTO, Void> {
    @Autowired
    private MailService emailService;

    @Override
    public Void process(EmailReaderItemDTO emailReaderItemDTO) throws Exception {
        this.emailService.sendHtmlMessage(emailReaderItemDTO.getTo(), emailReaderItemDTO.getSubject(),
                emailReaderItemDTO.getBody());
        return null;
    }

}
