package com.eazyBytes.eazySchool.controller;

import com.eazyBytes.eazySchool.model.Person;
import com.eazyBytes.eazySchool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class DashBoardController {

    private final PersonRepository personRepository ;

    public DashBoardController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/dashboard", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayDashBoard(Model model, Authentication authentication, HttpSession session){
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username",  person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        if (null != person.getEazyClass() && null != person.getEazyClass().getName()){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }

        session.setAttribute("loggedInPerson", person);
         return "dashboard.html";
    }
}
