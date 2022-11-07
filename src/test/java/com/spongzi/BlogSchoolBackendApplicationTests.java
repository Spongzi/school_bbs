package com.spongzi;

import com.spongzi.utlis.SendEmail;
import com.spongzi.utlis.TokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class BlogSchoolBackendApplicationTests {

    @Resource
    private SendEmail sendEmail;

    @Test
    void contextLoads() {
        sendEmail.sendEmail("lxlandsx@outlook.com", "123456");
    }

    @Test
    void testLoginStatus() {
        Claims verifyToken = TokenUtil.getClaims("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2Njc3MjgwMTMsInBob25lIjoiMTc3NjU2MzQ3MzEiLCJpZCI6MiwiZW1haWwiOiIyNTA1NTU1MjMxQHFxLmNvbSIsInVzZXJuYW1lIjoiYWRtaW4ifQ.xjLlUplNj6j0Y4LvWbOLp5o_03ZBsZBH5D6bedBTGr7v8tFxqpbd0ZlWdpu9PPySX2kePAb4e-vmmodouRYTvw");
        System.out.println(verifyToken);
    }

}
