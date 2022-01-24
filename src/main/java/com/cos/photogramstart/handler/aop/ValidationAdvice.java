package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component  // RestController. Service 모든 것들이 Component를 상속해서 만들어져 있다.
@Aspect
public class ValidationAdvice {
    
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..)))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web api 컨트롤러-------------------");
        // proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
        // profile 함수보다 먼저 실행

        return proceedingJoinPoint.proceed();   // profile 함수가 실행됨.
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..)))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web 컨트롤러-------------------");

        return proceedingJoinPoint.proceed();
    }

}
