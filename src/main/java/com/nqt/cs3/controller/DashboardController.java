package com.nqt.cs3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.nqt.cs3.service.StudentService;

@Controller
@EnableWebSecurity
public class DashboardController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/admin")
    public String dashboard(Model model){ 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        Object principal = auth.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            model.addAttribute("student", this.studentService.findByEmail(email));
        }
        return "admin/homepage";
    }
}
