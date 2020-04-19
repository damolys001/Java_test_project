package com.net.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String displayIndex() {
    	System.out.println("loding login page.");
        return "login";
    }
}
