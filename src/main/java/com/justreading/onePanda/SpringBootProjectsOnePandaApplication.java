package com.justreading.onePanda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootProjectsOnePandaApplication {

    public static void main(String[] args) {
        System.setProperty("https.protocols", "TLSv1.2");
        SpringApplication.run(SpringBootProjectsOnePandaApplication.class, args);
    }

}
