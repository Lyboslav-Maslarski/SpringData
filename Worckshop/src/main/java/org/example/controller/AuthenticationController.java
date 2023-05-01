package org.example.controller;

import org.example.models.dtos.RegistrationDTO;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public ModelAndView registerView(RegistrationDTO registrationDTO) {
        ModelAndView modelAndView = new ModelAndView("user/register");

        modelAndView.addObject("registrationDTO", registrationDTO);

        return modelAndView;
    }

    @PostMapping(value = "/users/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String doRegister(@Valid RegistrationDTO registrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/register";
        }

        this.userService.register(registrationDTO);

        return "/users/login";
    }
}
