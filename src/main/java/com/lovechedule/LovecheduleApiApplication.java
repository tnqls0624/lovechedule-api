package com.lovechedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LovecheduleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LovecheduleApiApplication.class, args);
        System.out.println("lovechedule api start success");
    }

}
