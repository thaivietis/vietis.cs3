package com.nqt.cs3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nqt.cs3.domain.Report;
import com.nqt.cs3.service.ReportService;
import com.nqt.cs3.service.StudentService;

@Controller
@EnableWebSecurity
public class DashboardController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/admin")
    public String dashboard(Model model){
        model.addAttribute("reports", this.reportService.getAllReport());
        return "admin/homepage";
    }


}
