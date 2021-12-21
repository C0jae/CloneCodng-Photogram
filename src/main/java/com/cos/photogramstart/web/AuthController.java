package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor    // final 필드를 DI 할때 필요
@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    // private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public final AuthService authService;
    
    @GetMapping("/auth/signin")
    public String signinForm() {

        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {

        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
        // 에러가 발생하면 에러 메세지를 Map에 받아오기
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());

                System.out.println("=====================");
                System.out.println(error.getDefaultMessage());
                System.out.println("=====================");
            }
                throw new CustomValidationException("유효성 검사 실패", errorMap);
        
        } else {
            // log.info(signupDto.toString());
            System.out.println("signupDto : " + signupDto.toString());

            // User <- SignupDto
            User user = signupDto.toEntity();
            System.out.println("user : " + user.toString());

            User userEntity = authService.signup(user);
            System.out.println("userEntity : " + userEntity);

            return "auth/signin";
        }

        
    }
    
}
