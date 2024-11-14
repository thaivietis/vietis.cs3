package com.nqt.cs3.component;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nqt.cs3.dto.ReaderItemDTO;
import com.nqt.cs3.dto.ReportDTO;

@Component
public class ReportItemProcess implements ItemProcessor<ReaderItemDTO, ReportDTO> {

    @Override
    public ReportDTO process(ReaderItemDTO item) throws Exception {
        ReportDTO report = new ReportDTO();
        report.setNameCourse(item.getNameCourse());
        report.setStudentRegistered(item.getStudentRegistered());
        report.setStartDate(LocalDate.now().with(java.time.DayOfWeek.MONDAY));
        report.setEndDate(LocalDate.now().with(java.time.DayOfWeek.SUNDAY));
        report.setReportDate(LocalDate.now());
        return report;
    }
}
