package com.rkr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rkr.mapper")
public class TJStateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TJStateApplication.class, args);
    }

}
