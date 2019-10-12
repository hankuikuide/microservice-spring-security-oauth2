package com.crhms.security.resourceserver.config;

import com.crhms.security.resourceserver.security.ServiceOAuth2RestTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.context.request.RequestContextListener;

import java.util.Arrays;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 9:56
 */
@Configuration
public class CustomConfig {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean("serviceClientCredentialsResourceDetails")
    @ConditionalOnMissingBean(name = "serviceClientCredentialsResourceDetails")
    @ConfigurationProperties("security.oauth2.client")
    public ClientCredentialsResourceDetails details() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        return details;
    }


    @Bean("serviceOAuth2RestTemplate")
    @ConditionalOnMissingBean(name = "serviceOAuth2RestTemplate")
    public ServiceOAuth2RestTemplate serviceOAuth2RestTemplate(@Qualifier("serviceClientCredentialsResourceDetails") ClientCredentialsResourceDetails details) {
        AccessTokenRequest request = new DefaultAccessTokenRequest();
        ServiceOAuth2RestTemplate template = new ServiceOAuth2RestTemplate(details,new DefaultOAuth2ClientContext(request));

        ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();

        AccessTokenProviderChain providers = new AccessTokenProviderChain(
                Arrays.asList(provider));

        template.setAccessTokenProvider(providers);

        return template;
    }

}
