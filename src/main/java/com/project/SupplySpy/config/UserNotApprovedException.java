package com.project.SupplySpy.config;

import org.springframework.security.core.AuthenticationException;

public class UserNotApprovedException extends AuthenticationException {
    public UserNotApprovedException(String msg) {
        super(msg);
    }
}