package com.example.scheduler21.controllers;

import com.example.scheduler21.exceptions.UserAlreadyRegisteredException;
import com.example.scheduler21.security.registration.MyUserService;
import com.example.scheduler21.security.registration.UserData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("register")
public class RegistrationController {

    private MyUserService myUserService;

    public RegistrationController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping
    public String register(Model model){

        model.addAttribute("userData", new UserData());

        return "register/register";
    }


    @PostMapping
    public String userRegistration(final @Valid UserData userData, final BindingResult bindingResult, final Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userData);
            return "register/register";
        }

        try {
            myUserService.register(userData);
        }
        catch (UserAlreadyRegisteredException e){

            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userData);
            return "register/register";

        }
        return "redirect:/";
    }

}
