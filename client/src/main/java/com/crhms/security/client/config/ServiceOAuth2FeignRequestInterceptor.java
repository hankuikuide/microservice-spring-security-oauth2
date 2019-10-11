package com.crhms.security.client.config;

import com.crhms.security.client.security.ServiceOAuth2RestTemplate;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.HashMap;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 14:52
 */
public class ServiceOAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private ServiceOAuth2RestTemplate oAuth2RestTemplate;

    public ServiceOAuth2FeignRequestInterceptor(ServiceOAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = oAuth2RestTemplate.getAccessToken().toString();

        template.header(AUTHORIZATION_HEADER,
                String.format("%s %s", BEARER_TOKEN_TYPE,token));
    }
}
