package com.adeliosys.microshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // To enable AccessLogFilter
// One package lower than usual, to automatically use components from the common module
public class MicroshopOrder {

    public static void main(String[] args) {
        SpringApplication.run(MicroshopOrder.class, args);
    }
}
