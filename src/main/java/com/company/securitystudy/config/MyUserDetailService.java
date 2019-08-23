package com.company.securitystudy.config;

import com.company.securitystudy.pojo.User;
import com.company.securitystudy.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * Author: Liuchong
 * Description: 用户认证配置
 * date: 2019/8/20 10:51
 */
@Configuration
@Primary
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUserNameLike(s);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        System.out.println("------------" + user.getPassword());

        return new org.springframework.security.core.userdetails.User(s, user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
