package com.example.userservice.aspect;

import com.example.userservice.dto.EventDto;
import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.enums.ApiName;
import com.example.userservice.mapper.EventMessageBuilder;
import com.example.userservice.service.AuthenticationService;
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

    private static final String NEW_USER = "NEW USER";
    private static final String ALL_USER = "ALL USER";
    private static final String NULL_USER_ID = "NULL";

    final UserEventsProducer userEventsProducer;
    final AuthenticationService authenticationService;

    @Pointcut("execution(public * com.example.userservice.controller.UserController.*(..))")
    public void userControllerPointCut() {
    }

    @Before("userControllerPointCut()")
    public void before(JoinPoint joinPoint) {

        String signature = String.valueOf(joinPoint.getSignature());
        ApiName api = RequestIdentifier.find(signature);
        String message;

        switch (api) {
            case REGISTER -> message = "Requested for new user registration";
            case GET_CURRENT_USER_DETAILS -> message = "Requested access current user details";
            case GET_DETAILS_BY_ID -> message = "Requested to access user data by id";
            case GET_ALL_DETAILS -> message = "Requested to access all user data";
            case UPDATE_USER_DETAILS -> message = "Requested to update user data";
            case DELETE_BY_ID -> message = "Requested to delete user data";
            default -> message = "Requested to perform Unknown operation";
        }
        publishMessage(message, api, joinPoint);
    }

    @AfterReturning("userControllerPointCut()")
    public void after(JoinPoint joinPoint) {

        String signature = String.valueOf(joinPoint.getSignature());
        ApiName api = RequestIdentifier.find(signature);
        String message;

        switch (api) {
            case REGISTER -> message = "Registration for new user completed successfully";
            case GET_CURRENT_USER_DETAILS -> message = "Accessed current user details successfully";
            case GET_DETAILS_BY_ID -> message = "Accessed user data by id successfully";
            case GET_ALL_DETAILS -> message = "Accessed all users data successfully";
            case UPDATE_USER_DETAILS -> message = "Updated user data successfully";
            case DELETE_BY_ID -> message = "Deleted user data successfully";
            default -> message = "Performed unknown operation successfully";
        }
        publishMessage(message, api, joinPoint);
    }

    @AfterThrowing(value = "userControllerPointCut()", throwing = "ex")
    public void afterException(JoinPoint joinPoint, Exception ex) {

        String signature = String.valueOf(joinPoint.getSignature());
        ApiName api = RequestIdentifier.find(signature);
        String message;

        switch (api) {
            case REGISTER -> message = "Registration unsuccessful.";
            case GET_DETAILS_BY_ID -> message = "Access user details unsuccessful.";
            case GET_ALL_DETAILS -> message = "Accessed all details data unsuccessful.";
            case UPDATE_USER_DETAILS -> message = "Updated user details unsuccessful.";
            case DELETE_BY_ID -> message = "Deletion of user details unsuccessful.";
            default -> message = "Perform of unknown operation is unsuccessful.";
        }
        message = message + " Error occurred: " + ex.getMessage();
        publishMessage(message, api, joinPoint);
    }

    private void publishMessage(String message, ApiName api, JoinPoint joinPoint) {
        String accessedFor = getAccessedForUserId(api, joinPoint);
        String accessedBy = getAccessedByUserId(api);
        EventDto eventDto = EventDto.builder()
                .accessedFor(accessedFor)
                .accessedBy(accessedBy)
                .message(message)
                .build();
        userEventsProducer.publishUserEvents(accessedBy, eventDto);
    }

    private String getAccessedForUserId(ApiName api, JoinPoint joinPoint) {
        String user;
        Object[] args = joinPoint.getArgs();
        switch (api) {
            case GET_DETAILS_BY_ID, DELETE_BY_ID -> {
                Object object = args[0];
                if (object instanceof Long) {
                    user = Long.toString((Long) args[0]);
                } else {
                    user = NULL_USER_ID;
                }
            }
            case UPDATE_USER_DETAILS -> {
                UserDetailsRequestDto dto = (UserDetailsRequestDto) args[0];
                user = String.valueOf(dto.getUserId());
            }
            case GET_CURRENT_USER_DETAILS -> user = authenticationService.getCurrentUser();
            case GET_ALL_DETAILS -> user = ALL_USER;
            case REGISTER -> user = NEW_USER;
            default -> user = NULL_USER_ID;
        }
        return user;
    }

    private String getAccessedByUserId(ApiName api) {
        if (api == ApiName.REGISTER) {
            return NEW_USER;
        }
        return authenticationService.getCurrentUser();
    }
}
