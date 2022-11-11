package com.spongzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动 main 函数
 *
 * @author spongzi
 */
@SpringBootApplication
@EnableScheduling
public class BlogSchoolBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSchoolBackendApplication.class, args);
    }

}
