package com.company.securitystudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/8/20 10:23
 */
@RestController
public class TestController {
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
}
