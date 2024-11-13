package com.nqt.cs3.component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.dto.EmailReaderItemDTO;
import com.nqt.cs3.service.EnrollmentService;

import jakarta.annotation.PostConstruct;

@Component
public class EmailItemReader implements ItemReader<EmailReaderItemDTO> {

    private final EnrollmentService enrollmentService;
    private List<Enrollment> enrollments;
    private List<EmailReaderItemDTO> enrollmentList;
    private int nextIndex = 0;

    public EmailItemReader(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostConstruct
    public void init() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY);

    }

    @Override
    public EmailReaderItemDTO read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

}
