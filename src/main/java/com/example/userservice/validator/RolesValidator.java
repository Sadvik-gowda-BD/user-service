package com.example.userservice.validator;

import com.example.userservice.exception.InvaildRoleException;

import static com.example.userservice.utils.Constant.*;

public class RolesValidator {

    public static void validateRole(String role) {
        if (null == role || !ROLES.contains(role)) {
            throw new InvaildRoleException(INVALID_ROLE_EX_MESSAGE);

        }
    }
}
