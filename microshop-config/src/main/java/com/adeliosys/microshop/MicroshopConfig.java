package com.adeliosys.microshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@ServletComponentScan // To enable AccessLogFilter
@EnableConfigServer
// One package lower than usual, to automatically use components from the common module
public class MicroshopConfig {

    public static void main(String[] args) {
        SpringApplication.run(MicroshopConfig.class, args);
    }
}
