package com.company.securitystudy.config;

import com.company.securitystudy.filter.ValidateCodeFilter;
import com.company.securitystudy.handler.MyAuthenticationAccessDeniedHandler;
import com.company.securitystudy.handler.MyAuthenticationSuccessHandler;
import com.company.securitystudy.handler.MyLogOutSuccessHandler;
import com.company.securitystudy.handler.MySessionExpiredStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Author: Liuchong
 * Description: spring 请求拦截配置
 * date: 2019/8/20 10:28
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;
    @Autowired
    private MyLogOutSuccessHandler myLogOutSuccessHandler;
    @Autowired
    private MyAuthenticationAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启验证码校验功能（将自定义拦截器放在用户密码拦截之前，进行验证码校验）。
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler) //权限不足处理类
                .and()
                .formLogin() // 表单登录
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/login")
                    .successHandler(myAuthenticationSuccessHandler) // 登录成功的处理器
                    .failureHandler(myAuthenticationSuccessHandler) // 登录失败的处理器
                .and()
                .rememberMe() //记住我功能
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(3600)
                    .userDetailsService(userDetailsService)
                .and() // 鉴权配置
                    .authorizeRequests() // 授权配置
                    .antMatchers("/login.html", "/exception","/css/*","/authentication/require", "/fail"
                    ,"/code/image", "session/invalid","/signout/success").permitAll()
                    .anyRequest()  // 所有请求
                    .authenticated()  // 都需要认证
                .and()
                    .logout()
                    .logoutUrl("/signout")
//                    .logoutSuccessUrl("/signout/success") //注销成功过后的URL后
                    .logoutSuccessHandler(myLogOutSuccessHandler) // 注销成功后的处理器
                    .deleteCookies("JSESSIONID")
                .and()
                    .csrf().disable()
                    .sessionManagement() // 添加session管理器
                    .invalidSessionUrl("/session/invalid") // Session失效后跳转到这个链接
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true) // 配置登录到最大人数以后不允许后面的登录
                    .expiredSessionStrategy(sessionExpiredStrategy);


    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }


    /**
     * 配置加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
