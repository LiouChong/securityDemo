package com.company.securitystudy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/8/20 11:36
 */
@RestController
public class BrowserSecurityController {
    // 该对象会将所有请求缓存
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取本次请求对象SaveRequest
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            // 获取本次请求的URL
            String targetUrl = savedRequest.getRedirectUrl();
            // 如果URL以.html结尾，则跳转到login.html页面
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                return "访问的资源需要身份认证！";
            } else  {
                redirectStrategy.sendRedirect(request, response, "/login.html");
            }
        }
        return "访问的资源需要身份认证！";
    }

}
