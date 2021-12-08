package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {
    
    @GetMapping("/auth/signin")
    public String signinForm() {

        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {

        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup() {

        return "auth/signin";
    }
    
}
