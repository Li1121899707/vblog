package com.hitwh.vblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hitwh.vblog.mapper")
@SpringBootApplication
public class VblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VblogApplication.class, args);
    }

}
