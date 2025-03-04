package com.example.demo.aspects;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class aspects {
    private static final Logger logger = LoggerFactory.getLogger(aspects.class);

    @Before("execution(* com.example.demo.controllers.controllers.*(..))")
    public void logBeforeMethod() {
        System.out.println("method will be called.");
}

}
