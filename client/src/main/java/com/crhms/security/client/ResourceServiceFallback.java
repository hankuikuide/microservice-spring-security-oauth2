package com.crhms.security.client;

import org.springframework.stereotype.Service;

@Service
public class ResourceServiceFallback implements ResourceService  {
    @Override
    public String hello() {
        return "This is Fallback message";
    }
}
