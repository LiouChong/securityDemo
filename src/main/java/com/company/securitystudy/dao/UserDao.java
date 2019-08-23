package com.company.securitystudy.dao;

import com.company.securitystudy.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/8/22 16:02
 */
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUserNameLike(String username);
}
