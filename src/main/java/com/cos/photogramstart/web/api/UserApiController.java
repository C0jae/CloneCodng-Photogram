package com.cos.photogramstart.web.api;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@RestController // 데이터로만 응답할거기 때문에 RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
        @PathVariable int id, 
        @Valid UserUpdateDto userUpdateDto, 
        BindingResult bindingResult, // 꼭 Valid가 적혀있는 다음 파라미터에 적어야한다.
        @AuthenticationPrincipal PrincipalDetails principalDetails) {

            if (bindingResult.hasErrors()) {
                Map<String, String> errorMap = new HashMap<>();
    
                for (FieldError error : bindingResult.getFieldErrors()) {
                    errorMap.put(error.getField(), error.getDefaultMessage());
    
                    System.out.println("=====================");
                    System.out.println(error.getDefaultMessage());
                    System.out.println("=====================");
                }
                    throw new CustomValidationAPIException("유효성 검사 실패", errorMap);
            
            } else {
                User userEntity = userService.userUpdate(id, userUpdateDto.toEntity());
                principalDetails.setUser(userEntity);   // 세션정보 변경
        
                return new CMRespDto<>(1, "회원수정 완료", userEntity);
            }   // else end
    }
    
}
