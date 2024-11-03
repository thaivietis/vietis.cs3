package com.nqt.cs3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @GetMapping("/user")
    public String getMethodName(@RequestParam String param) {
        return "user";
    }

}
