package com.crhms.security.client.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "resource-service",
        url = "http://localhost:9003/",
        fallback = ResourceService.ResourceServiceFallback.class )
public interface ResourceService {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello();

    @Component
    class ResourceServiceFallback implements ResourceService  {
        @Override
        public String hello() {
            return "This is Fallback message";
        }
    }
}
