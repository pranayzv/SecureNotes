package com.pzvapps.SecureNotes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String getHome(){return "Home";}

    @GetMapping("/contact")
    public String getContact(){return "Contact";}

}
