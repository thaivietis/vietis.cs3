package com.nqt.cs3.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqt.cs3.domain.Report;
import com.nqt.cs3.dto.ReportDTO;
import com.nqt.cs3.repository.ReportRepository;
import com.nqt.cs3.service.IService.IReportService;

@Service
public class ReportService implements IReportService {
    
    @Autowired
    public ReportRepository reportRepository;

    @Override
    public List<Report> getAllReport(){
        return this.reportRepository.findAll();
    }

    @Override
    public Report createNewReport() {
        Report newReport = new Report();
        newReport.setNameReport("Báo cáo tuần");
        newReport.setStartDate(LocalDate.now().with(java.time.DayOfWeek.MONDAY));
        newReport.setEndDate(LocalDate.now().with(java.time.DayOfWeek.SUNDAY));
        newReport.setReportDate(LocalDate.now());
        newReport.setPathFile("");
        newReport.setCreatedBy("Hệ thống");
        return this.reportRepository.save(newReport);
    }
}
