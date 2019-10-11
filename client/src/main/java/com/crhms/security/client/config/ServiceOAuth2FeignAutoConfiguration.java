package com.crhms.security.client.config;

import com.crhms.security.client.security.ServiceOAuth2RestTemplate;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 14:48
 */
@Configuration
public class ServiceOAuth2FeignAutoConfiguration{

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(
            @Qualifier("serviceOAuth2RestTemplate") ServiceOAuth2RestTemplate template) {
        return new ServiceOAuth2FeignRequestInterceptor(template);
    }


}
