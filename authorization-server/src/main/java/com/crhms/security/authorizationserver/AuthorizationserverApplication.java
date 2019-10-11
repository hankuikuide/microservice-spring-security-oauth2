package com.crhms.security.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@SpringBootApplication
@EnableResourceServer
public class AuthorizationserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationserverApplication.class, args);
    }

}
