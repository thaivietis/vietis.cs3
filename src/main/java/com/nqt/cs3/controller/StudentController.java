package com.nqt.cs3.controller;

import com.nqt.cs3.domain.Student;
import com.nqt.cs3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    public StudentService studentService;

    @GetMapping("/student")
    public String getAllStudent(Model model) {
        List<Student> students = this.studentService.findAll();
        model.addAttribute("students", students);
        return "student/show";
    }

    @GetMapping("/student/detail/{id}")
    public String getDetailStudent(@PathVariable("id") long id, Model model) {
        return "student/detail";
    }

    @GetMapping("/student/create")
    public String getCreateStudent(Model model) {
        return "student/create";
    }

    @GetMapping("/student/update/{id}")
    public String getUpdateStudent(@PathVariable("id") long id, Model model) {
        return "student/update";
    }

    @GetMapping("/student/delete/{id}")
    public String getDeleteStudent(@PathVariable("id") long id, Model model) {
        return "student/delete";
    }

}
