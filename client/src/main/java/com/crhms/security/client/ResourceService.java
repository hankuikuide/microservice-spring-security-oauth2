package com.crhms.security.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "resource-service", url = "http://localhost:9003/auth", fallback = ResourceServiceFallback.class )
public interface ResourceService {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String hello();
}
