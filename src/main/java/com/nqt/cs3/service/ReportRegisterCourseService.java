package com.nqt.cs3.service;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.dto.ReportWeekDTO;

@Component
public class ReportRegisterCourseService implements ItemProcessor<ReaderItemDTO, ReportWeekDTO> {

    @Override
    public ReportWeekDTO process(ReaderItemDTO item) throws Exception {
        ReportWeekDTO report = new ReportWeekDTO();
        report.setNameCourse(item.getNameCourse());
        report.setStudentRegistered(item.getStudentRegistered());
        report.setStartDate(LocalDate.now().with(java.time.DayOfWeek.MONDAY));
        report.setEndDate(LocalDate.now().with(java.time.DayOfWeek.SUNDAY));
        report.setReportDate(LocalDate.now());
        return report;
    }
}
