package com.eazyBytes.eazySchool.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {

    @RequestMapping( value={"/home"," ","/"})
    public String displayHomePage(){

        return "home.html";
    }
}
