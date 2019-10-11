package com.crhms.security.client.security;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 9:33
 */
public class ServiceOAuth2RestTemplate extends OAuth2RestTemplate {

    public ServiceOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
        this(resource, new DefaultOAuth2ClientContext());
    }

    public ServiceOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super(resource, context);
    }
}
