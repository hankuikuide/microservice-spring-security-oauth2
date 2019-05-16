package com.crhms.security.resource2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class Resource2serverApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resource2serverApplication.class, args);
    }

}
