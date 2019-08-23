package com.company.securitystudy.controller;

import com.company.securitystudy.exception.MyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/8/20 10:23
 */
@RestController
public class PageController {
    @GetMapping("hello")
    public String hello() {
        return "hello spring security!!!!";
    }

    @GetMapping("success")
    public String successPage() {
        return "login success";
    }

    @GetMapping("fail")
    public String filePage() {
        return "login fail";
    }

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('admin')")
    public String getadminPage() {
        return "u r admin";
    }

    @GetMapping("exception")
    public void test() throws MyException {
        throw new MyException("测试异常");
    }
}
