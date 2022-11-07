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
    private SendEmail sendEmail;

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        sendEmail.sendEmail("lxlandsx@outlook.com", "123456");
    }

    @Test
    void testLoginStatus() {
        Claims verifyToken = TokenUtil.getClaims("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2Njc3OTAwODcsInBob25lIjoiMTc3NjU2MzQ3MzEiLCJpZCI6MiwiZW1haWwiOiIyNTA1NTU1MjMxQHFxLmNvbSIsInVzZXJuYW1lIjoiYWRtaW4ifQ.Be64Nedlyq3ubzRxzmK9tGaZKPhz8O9zMrES1t6bJazM_qf1coI4tmAaDKVh87Tol3eWClg4j-nEOfWc1uVbkA");
        System.out.println(verifyToken);
    }

    @Test
    void testPassword() {
        System.out.println(userService.encryptionPassword("12345678", "spongzi_admin"));
    }

    @Test
    void parseToken() {
        Claims claims = TokenUtil.getClaims("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2Njc4MjIwNzgsInBob25lIjoiMTUxNTU1Mzc2NjYiLCJpZCI6MiwiZW1haWwiOiIyNTA1NTU1MjMxQHFxLmNvbSIsInVzZXJuYW1lIjoic3Bvbmd6aV9hZG1pbiJ9.iDak0oF95oN2rg4x4WRq72Wo349xIzZ7tcYT10EvBEaS4Ic-_NJ5sieOZEoWWq_IyOm1WRnZzpdetEWpsFdyJQ");
        Object id = claims.get("id");
        Date expiration = claims.getExpiration();
        System.out.println(id);
        System.out.println(expiration);
        System.out.println(new Date(System.currentTimeMillis() + 604800000));
    }
}
