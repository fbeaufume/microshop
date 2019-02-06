package com.adeliosys.microshop;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MicroshopAdmin {

    public static void main(String[] args) {
        SpringApplication.run(MicroshopAdmin.class, args);
    }
}
