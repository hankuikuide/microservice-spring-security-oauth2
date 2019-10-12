package com.crhms.security.resource2server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("This is a message from resouce-2 server !");

        return "This is a message from resouce server2 !";
    }
}
