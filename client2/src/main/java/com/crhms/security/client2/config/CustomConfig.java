package com.crhms.security.client2.config;

import com.crhms.security.client2.security.ServiceOAuth2RestTemplate;
import com.crhms.security.client2.security.ZuulAuthHeaderFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
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

    /**
     * 这里注入了两个OAuth2RestTemplate，一个是授权码模式的，一个是客户端模式的，
     * 实际上只需要一个就可以
     * OAuth2RestTemplate
     * @param oAuth2RestTemplate
     * @param template
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ZuulFilter.class)
    public ZuulFilter zuulFilter(OAuth2RestTemplate oAuth2RestTemplate, @Qualifier("serviceOAuth2RestTemplate")ServiceOAuth2RestTemplate template) {
        ZuulAuthHeaderFilter filter = new ZuulAuthHeaderFilter(oAuth2RestTemplate);
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

    /**
     * 注入授权码模式的OAuth2RestTemplate
     * @param factory
     * @return
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(UserInfoRestTemplateFactory factory) {
        OAuth2RestTemplate restTemplate = factory.getUserInfoRestTemplate();
        return restTemplate;
    }


    /**
     * 注入客户端模式的OAuth2RestTemplate所需的ClientCredentialsResourceDetails
     * @param
     * @return
     */
    @Bean("serviceClientCredentialsResourceDetails")
    @ConditionalOnMissingBean(name = "serviceClientCredentialsResourceDetails")
    @ConfigurationProperties("security.oauth2.client")
    public ClientCredentialsResourceDetails details() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        return details;
    }

    /**
     * 注入客户端模式的OAuth2RestTemplate
     * @param
     * @return
     */
    @Bean("serviceOAuth2RestTemplate")
    @ConditionalOnMissingBean(name = "serviceOAuth2RestTemplate")
    public ServiceOAuth2RestTemplate serviceOAuth2RestTemplate(@Qualifier("serviceClientCredentialsResourceDetails") ClientCredentialsResourceDetails details) {

        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        final ServiceOAuth2RestTemplate oAuth2RestTemplate = new ServiceOAuth2RestTemplate(details,new DefaultOAuth2ClientContext(atr));

        ClientCredentialsAccessTokenProvider authCodeProvider = new ClientCredentialsAccessTokenProvider();

        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));

        oAuth2RestTemplate.setAccessTokenProvider(provider);

        return oAuth2RestTemplate;
    }
}
