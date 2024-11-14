package com.nqt.cs3.component;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nqt.cs3.dto.EmailReaderItemDTO;

@Component
public class EmailItemProcess implements ItemProcessor<EmailReaderItemDTO, EmailReaderItemDTO> {

    @Override
    public EmailReaderItemDTO process(EmailReaderItemDTO item) throws Exception {
        return item;
    }

}
