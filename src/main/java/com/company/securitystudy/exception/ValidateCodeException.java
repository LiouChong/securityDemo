package com.company.securitystudy.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Author: Liuchong
 * Description: 自定义异常
 * date: 2019/8/20 17:22
 */
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = 5022575393500654458L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
