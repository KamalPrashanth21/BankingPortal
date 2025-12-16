package com.banking.portal.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.banking.portal.annotations.*;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Around("@annotation(com.banking.portal.annotations.AuditUserCreation)") //this audits the user creation method
    public Object logUserCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("User Registration started - Passed Arguments : {}", joinPoint.getArgs());
//        System.out.println("Aop is working!");//
        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); //this is where the actual method call takes place
        Long endTime = System.currentTimeMillis();

        log.info("User Registration ended - Returned Value : {}", result);
        log.info("User Registration completed in {} ms", (endTime - startTime)); //calculates the total time taken for the function to execute
        return result; //returns the result of the service method, which was intercepted, to the controller.

    }

    @Around("@annotation(com.banking.portal.annotations.AuditFundTransfer)") //this audits/tracks the fund transfer method
    public Object logFundTransfer(ProceedingJoinPoint joinPoint, AuditFundTransfer auditFundTransfer) throws Throwable {
        log.info("Fund Transfer started - Passed Arguments : {}", joinPoint.getArgs());

        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); 
        Long endTime = System.currentTimeMillis();

        log.info("Fund Transfer ended - Returned Value : {}", result);
        log.info("Fund Transfer completed in {} ms", (endTime - startTime)); 
        return result; 

    }
    
}
