package com.spongzi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class BlogSchoolBackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DigestUtils.md5DigestAsHex("123456123456spongzi".getBytes(StandardCharsets.UTF_8)));
    }

}
