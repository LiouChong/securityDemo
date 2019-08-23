package com.company.securitystudy.config;

import com.company.securitystudy.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启验证码校验功能（将自定义拦截器放在用户密码拦截之前，进行验证码校验）。
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
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
                    .antMatchers("/login.html","/css/*","/authentication/require", "/fail"
                    ,"/code/image").permitAll()
                    .anyRequest()  // 所有请求
                    .authenticated()  // 都需要认证
                .and().csrf().disable();

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
