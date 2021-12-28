package com.cos.photogramstart.handler;


import com.cos.photogramstart.handler.ex.CustomAPIException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    // ? : 리턴타입 알아서 찾은 후 적용
    public String validationException(CustomValidationException e) {
        //CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할 때는 Script가 좋음
        // 2. Ajax 통신 - CMRespDto
        // 3. Android 통신 - CMRespDto

        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());

        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomException.class)
    // ? : 리턴타입 알아서 찾은 후 적용
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomValidationAPIException.class)
    public ResponseEntity<?> validationAPIException(CustomValidationAPIException e) {

        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomAPIException.class)
    public ResponseEntity<?> aPIException(CustomAPIException e) {

        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
    
}
