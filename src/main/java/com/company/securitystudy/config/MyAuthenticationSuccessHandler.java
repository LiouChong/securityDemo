package com.company.securitystudy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Liuchong
 * Description: 设置登录成功或失败操作
 * date: 2019/8/20 16:16
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler , AuthenticationFailureHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private ObjectMapper mapper;

    /**
     * 重写登录成功后跳转的页面
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            redirectStrategy.sendRedirect(request, response, "/success");
            return;
        }

        String url = savedRequest.getRedirectUrl();
        if (url.endsWith("/")) {
            redirectStrategy.sendRedirect(request, response,"/success");
            return;

        }
        // 返回上一个请求被拦截页面
        redirectStrategy.sendRedirect(request, response, url);
    }

    /**
     * 重写登录失败后跳转的页面
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(mapper.writeValueAsString(e.getMessage()));
    }
}
