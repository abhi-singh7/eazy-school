package com.eazyBytes.eazySchool.controller;


import com.eazyBytes.eazySchool.model.Contact;
import com.eazyBytes.eazySchool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class ContactController {



    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

//    @PostMapping("/saveMsg")
//    public ModelAndView saveMessage (@RequestParam String name, @RequestParam String mobileNum,
//                         @RequestParam String email, @RequestParam String subject,
//                         @RequestParam String message)
//    {
//        log.info("Name :{}", name);
//            log.info("Mobile Number :"+ mobileNum);
//            log.info("Email address :"+ email);
//            log.info("Subject :"+subject);
//            log.info("Message :"+ message);
//            return new ModelAndView("redirect:/contact");
//    }


    @PostMapping("/saveMsg")
    public String saveMessage(@Valid @ModelAttribute Contact contact, Errors errors){
        if (errors.hasErrors()){
            log.error("Contact form validation failed due to : {}", errors.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model){
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg", method = RequestMethod.GET)
    public String closeMsg(@RequestParam int id){
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages";
    }
}
