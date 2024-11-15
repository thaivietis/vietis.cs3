package com.nqt.cs3.service.report;

import java.util.List;

import com.nqt.cs3.domain.Report;

public interface IReportService {
    List<Report> getAllReport();

    Report createNewReport();
}
