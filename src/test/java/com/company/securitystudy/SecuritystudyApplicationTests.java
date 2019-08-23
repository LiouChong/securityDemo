package com.company.securitystudy;

import com.company.securitystudy.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecuritystudyApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsername() {
        String username = "user";
        System.out.println(userDao.findByUserNameLike(username));
    }

}
