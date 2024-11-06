package com.nqt.cs3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nqt.cs3.constant.RoleEnum;
import com.nqt.cs3.domain.Student;
import com.nqt.cs3.dto.RegisterDTO;
import com.nqt.cs3.service.StudentService;

@Controller
public class HomepageController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String homepage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        Object principal = auth.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            model.addAttribute("student", this.studentService.findByEmail(email));
        }
        return "client/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("register", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("register") RegisterDTO registerDTO) {
        Student newStudent = this.studentService.registerDtoToStudent(registerDTO);
        newStudent.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newStudent.setRole(this.studentService.getRoleByName(RoleEnum.USER));
        this.studentService.save(newStudent);
        return "redirect:/login";
    }
}
