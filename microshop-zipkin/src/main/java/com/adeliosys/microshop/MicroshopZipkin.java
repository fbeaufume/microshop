package com.adeliosys.microshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class MicroshopZipkin {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "zipkin-server");
        SpringApplication.run(MicroshopZipkin.class, args);
    }
}
