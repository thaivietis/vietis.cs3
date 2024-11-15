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

import com.nqt.cs3.domain.Course;
import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.service.enrollment.EnrollmentService;

import jakarta.annotation.PostConstruct;

@Component
public class ReportItemReader implements ItemReader<ReaderItemDTO> {

    private final EnrollmentService enrollmentService;
    private List<Enrollment> enrollments;
    Map<Course, Long> courseEnrollmentCount;
    private List<ReaderItemDTO> enrollmentList;
    private int nextIndex = 0;

    public ReportItemReader(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostConstruct
    public void init() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY);
        enrollments = enrollmentService.findAll().stream()
                .filter(enrollment -> !enrollment.getEnrollmentDate().isBefore(startOfWeek) &&
                        !enrollment.getEnrollmentDate().isAfter(endOfWeek))
                .collect(Collectors.toList());
        courseEnrollmentCount = enrollments.stream()
                .collect(Collectors.groupingBy(Enrollment::getCourse, Collectors.counting()));
        enrollmentList = courseEnrollmentCount.entrySet().stream()
                .map(entry -> new ReaderItemDTO(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public ReaderItemDTO read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (nextIndex < enrollmentList.size()) {
            return enrollmentList.get(nextIndex++);
        } else {
            return null;
        }
    }
}
