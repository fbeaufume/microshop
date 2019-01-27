package com.adeliosys.microshop.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@ServletComponentScan
@EnableConfigServer
public class MicroshopConfig {

	public static void main(String[] args) {
		SpringApplication.run(MicroshopConfig.class, args);
	}
}
