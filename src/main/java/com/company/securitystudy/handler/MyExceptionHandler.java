package com.company.securitystudy.handler;

import com.company.securitystudy.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/8/23 15:56
 */
@ControllerAdvice
public class MyExceptionHandler {

    //@TODO：捕获不了security抛出的AccessDeniedException
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public String AccessDeniedExceptionHandler() {
        return "您没有权限！";
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public String testExceptionCatch() {
        return "捕获到了异常！";
    }
}
