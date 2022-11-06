package com.spongzi;

import com.spongzi.utlis.SendEmail;
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

}
