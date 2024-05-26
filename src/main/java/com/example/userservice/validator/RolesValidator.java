package com.example.userservice.validator;

import com.example.userservice.exception.InvalidRoleException;

import static com.example.userservice.utils.Constant.*;

public class RolesValidator {

    public static void validateRole(String role) {
        if (null == role || !ROLES.contains(role)) {
            throw new InvalidRoleException(INVALID_ROLE_EX_MESSAGE);

        }
    }
}
