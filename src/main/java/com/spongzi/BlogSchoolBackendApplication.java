package com.spongzi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动 main 函数
 *
 * @author spongzi
 */
@SpringBootApplication
public class BlogSchoolBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSchoolBackendApplication.class, args);
    }

}
