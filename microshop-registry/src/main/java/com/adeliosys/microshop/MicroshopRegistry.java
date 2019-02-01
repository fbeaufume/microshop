package com.adeliosys.microshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@ServletComponentScan // To enable AccessLogFilter
@EnableEurekaServer
// One package lower than usual, to automatically use components from the common module
public class MicroshopRegistry {

    public static void main(String[] args) {
        SpringApplication.run(MicroshopRegistry.class, args);
    }
}
