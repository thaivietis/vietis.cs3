package com.nqt.cs3.service;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.dto.ReportDTO;

@Component
public class ReportRegisterCourseService implements ItemProcessor<ReaderItemDTO, ReportDTO> {

    @Override
    public ReportDTO process(ReaderItemDTO item) throws Exception {
        System.out.println("Start process");
        ReportDTO report = new ReportDTO();
        report.setNameCourse(item.getNameCourse());
        report.setStudentRegistered(item.getStudentRegistered());
        report.setStartDate(LocalDate.now().with(java.time.DayOfWeek.MONDAY));
        report.setEndDate(LocalDate.now().with(java.time.DayOfWeek.SUNDAY));
        report.setReportDate(LocalDate.now());
        return report;
    }
}
