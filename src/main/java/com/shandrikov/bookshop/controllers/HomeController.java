package com.shandrikov.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = {"/", "/api", "/api/"})
    public String getEmpty(){return "redirect:/api/books";}
}
