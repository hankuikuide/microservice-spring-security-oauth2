package com.crhms.security.client;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

public class OAuth2FeignRequestInterceptor implements RequestInterceptor {


    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final OAuth2RestTemplate oAuth2RestTemplate;


    public OAuth2FeignRequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Override
    public void apply(RequestTemplate template) {
        System.out.println("Constructing Header "+AUTHORIZATION_HEADER+" for Token " + BEARER_TOKEN_TYPE +":" +oAuth2RestTemplate.getAccessToken().toString());
        template.header(AUTHORIZATION_HEADER,
                String.format("%s %s",
                        BEARER_TOKEN_TYPE,
                        oAuth2RestTemplate.getAccessToken().toString()));

    }
}