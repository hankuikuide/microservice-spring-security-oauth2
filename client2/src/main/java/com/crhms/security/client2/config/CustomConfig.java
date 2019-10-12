package com.crhms.security.client2.config;

import com.crhms.security.client2.security.ServiceOAuth2RestTemplate;
import com.crhms.security.client2.security.ZuulAuthHeaderFilter;
import com.netflix.zuul.ZuulFilter;
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
 * @date ：Created in 2019/10/11 19:46
 */
@Configuration
public class CustomConfig {

    @Bean
    @ConditionalOnMissingBean(ZuulFilter.class)
    public ZuulFilter zuulFilter(@Qualifier("serviceOAuth2RestTemplate")ServiceOAuth2RestTemplate template) {
        ZuulAuthHeaderFilter filter = new ZuulAuthHeaderFilter(template);
        return filter;
    }


    /**
     * 如果不加这个Listenner，不显示登录界面
     * 在这个类中，spring将每一个请求开始前都将请求进行了一次封装并设置了一个threadLocal，
     * 这样我们在请求处理的任何地方都可以通过这个threadLocal获取到请求对象
     * @return
     */
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
    public ServiceOAuth2RestTemplate cisServiceOAuth2RestTemplate(@Qualifier("serviceClientCredentialsResourceDetails") ClientCredentialsResourceDetails details) {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        final ServiceOAuth2RestTemplate oAuth2RestTemplate = new ServiceOAuth2RestTemplate(details,new DefaultOAuth2ClientContext(atr));

        ClientCredentialsAccessTokenProvider authCodeProvider = new ClientCredentialsAccessTokenProvider();

        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));

        oAuth2RestTemplate.setAccessTokenProvider(provider);

        return oAuth2RestTemplate;
    }
}
