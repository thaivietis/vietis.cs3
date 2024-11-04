package com.nqt.cs3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomepageController {
    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("title", "Hello Cs3");
        return "/index";
    }
}
