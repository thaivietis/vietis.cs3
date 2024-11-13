package com.nqt.cs3.service.IService;

import java.util.List;

import com.nqt.cs3.domain.Report;
import com.nqt.cs3.dto.ReportDTO;

public interface IReportService {
    List<Report> getAllReport();

    Report createNewReport();
}
