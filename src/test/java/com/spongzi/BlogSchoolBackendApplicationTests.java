package com.spongzi;

import com.spongzi.service.UserService;
import com.spongzi.utlis.SendEmail;
import com.spongzi.utlis.TokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;

@SpringBootTest
class BlogSchoolBackendApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void test() {
        System.out.println(userService.encryptionPassword("123456", "admin"));
    }
}
