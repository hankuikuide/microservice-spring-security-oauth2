package com.crhms.security.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //@Autowired
    private OAuth2RestTemplate restTemplate;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/hello")
    public String hello (){
        ResponseEntity<String> restExchange =
                restTemplate.exchange(
                        "http://localhost:9002/auth/hello",
                        HttpMethod.GET,null,String.class);

        String result = restExchange.getBody();
        return result;
    }

    @GetMapping("/helloResource")
    public String helloResource (){
//        ResponseEntity<String> restExchange =
//                restTemplate.exchange(
//                        "http://localhost:9003/auth/hello",
//                        HttpMethod.GET,null,String.class);
//
//        String result = restExchange.getBody();

        String result = resourceService.hello();
        return result;
    }
}
