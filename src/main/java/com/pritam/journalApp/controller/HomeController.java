package com.pritam.journalApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "redirect:https://journal-app-backend-springboot-obkk.onrender.com/journal/swagger-ui/index.html";
    }
}
