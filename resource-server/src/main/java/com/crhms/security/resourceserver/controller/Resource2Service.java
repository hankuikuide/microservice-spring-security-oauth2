package com.crhms.security.resourceserver.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "resource2-service",
        url = "http://localhost:9004/",
        fallback = Resource2Service.ResourceServiceFallback.class )
public interface Resource2Service {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello();

    @Component
    class ResourceServiceFallback implements Resource2Service  {
        @Override
        public String hello() {
            return "This is Fallback message";
        }
    }
}
