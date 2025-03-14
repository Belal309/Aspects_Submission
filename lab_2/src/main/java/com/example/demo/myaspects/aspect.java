package com.example.demo.myaspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class aspect {
    @Before("execution(* com.example.demo.users.UserController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("**New request is coming**");
        System.out.println("Method: " + joinPoint.getSignature().getName());
    }
}
