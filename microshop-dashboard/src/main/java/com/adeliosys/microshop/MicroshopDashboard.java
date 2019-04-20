package com.adeliosys.microshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class MicroshopDashboard {

    public static void main(String[] args) {
        SpringApplication.run(MicroshopDashboard.class, args);
    }
}
