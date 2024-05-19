package com.example.userservice.aspect;

import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.enums.ApiName;
import com.example.userservice.service.UserEventsProducer;
import com.example.userservice.utils.RequestIdentifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserControllerAspect {


    final UserEventsProducer userEventsProducer;

    @Pointcut("execution(public * com.example.userservice.controller.UserController.*(..))")
    public void userControllerPointCut() {

    }

    @Before("userControllerPointCut()")
    public void before(JoinPoint joinPoint) {
//        log.info("*************************Before*********************************");
//        log.info(Arrays.toString(joinPoint.getArgs()));
//        log.info(joinPoint.getKind());
//        log.info(String.valueOf(joinPoint.getSignature()));

        String signature = String.valueOf(joinPoint.getSignature());

        ApiName api = RequestIdentifier.find(signature);
        String message;

        switch (api) {
            case REGISTER -> message = "Requested for user registration";
            case GET_DETAILS_BY_ID -> message = "Accessing user data";
            case GET_ALL_DETAILS -> message = "Accessing all user data";
            case UPDATE_USER_DETAILS -> message = "Updating users data";
            case DELETE_BY_ID -> message = "Requested to delete user data";
            default -> message = "Requested to perform Unknown operation";
        }
        userEventsProducer.publishUserEvents(getUserId(api, joinPoint), message);
    }

    @AfterReturning("userControllerPointCut()")
    public void after(JoinPoint joinPoint) {
//        log.info("*************************After*********************************");
//        log.info(Arrays.toString(joinPoint.getArgs()));
//        log.info(joinPoint.getKind());
//        log.info(String.valueOf(joinPoint.getSignature()));

        String signature = String.valueOf(joinPoint.getSignature());
        ApiName api = RequestIdentifier.find(signature);
        String message;

        switch (api) {
            case REGISTER -> message = "Registration completed";

            case GET_DETAILS_BY_ID -> message = "Accessed user data successfully";
            case GET_ALL_DETAILS -> message = "Accessed all users data successfully";
            case UPDATE_USER_DETAILS -> message = "Updated user data successfully";
            case DELETE_BY_ID -> message = "Deleted user data successfully";
            default -> message = "Performed unknown operation successfully";
        }
        userEventsProducer.publishUserEvents(getUserId(api, joinPoint), message);

    }

    @AfterThrowing(value = "userControllerPointCut()", throwing = "ex")
    public void afterException(JoinPoint joinPoint, Exception ex) {
//        log.info("*************************Exception*********************************");
//        log.info(Arrays.toString(joinPoint.getArgs()));
//        log.info(joinPoint.getKind());
//        log.info(String.valueOf(joinPoint.getSignature()));
//        log.info("Exception message " + ex.getMessage());

        String signature = String.valueOf(joinPoint.getSignature());
        ApiName api = RequestIdentifier.find(signature);
        String message;

        switch (api) {
            case REGISTER -> message = "Registration unsuccessful.";
            case GET_DETAILS_BY_ID -> message = "Access user data unsuccessful.";
            case GET_ALL_DETAILS -> message = "Accessed all users data unsuccessful.";
            case UPDATE_USER_DETAILS -> message = "Updated user data unsuccessful.";
            case DELETE_BY_ID -> message = "Deletion of user data unsuccessful.";
            default -> message = "Performed unknown operation unsuccessful.";
        }
        message = message + " Error occurred: " + ex.getMessage();
        userEventsProducer.publishUserEvents(getUserId(api, joinPoint), message);

    }

    private String getUserId(ApiName api, JoinPoint joinPoint) {
        long userId;
        Object[] args = joinPoint.getArgs();
        if (null == args || args.length == 0) {
            return "0";
        }
        Object arg = args[0];
        switch (api) {
            case GET_DETAILS_BY_ID, DELETE_BY_ID -> userId = (long) arg;
            case UPDATE_USER_DETAILS -> {
                UserDetailsRequestDto dto = (UserDetailsRequestDto) arg;
                userId = dto.getUserId();
            }
            default -> userId = 0;
        }
        return String.valueOf(userId);
    }


}
