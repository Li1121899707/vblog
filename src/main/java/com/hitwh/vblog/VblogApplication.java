package com.hitwh.vblog;

import com.hitwh.vblog.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@MapperScan("com.hitwh.vblog.mapper")
@Import(SpringUtil.class)
@SpringBootApplication
public class VblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VblogApplication.class, args);
    }

}
