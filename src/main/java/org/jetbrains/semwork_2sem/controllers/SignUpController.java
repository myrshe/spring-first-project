package org.jetbrains.semwork_2sem.controllers;

import freemarker.template.TemplateException;
import org.jetbrains.semwork_2sem.dto.UserForm;
import org.jetbrains.semwork_2sem.services.intefaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "sign_up_page";
    }

    @PostMapping("/signUp")
    public String signUp(UserForm userForm) throws TemplateException, IOException {
        signUpService.addUser(userForm);
        return "redirect:/signIn";
    }
}
