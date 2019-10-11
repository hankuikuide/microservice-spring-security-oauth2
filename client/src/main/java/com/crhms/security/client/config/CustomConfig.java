package com.crhms.security.client.config;

import com.crhms.security.client.security.ServiceOAuth2RestTemplate;
import com.crhms.security.client.security.SsoLogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.context.request.RequestContextListener;

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

    //@Value("${aa}")
    private String logOutUri;

    // @Autowired(required = false)
    // @Qualifier("serviceOAuth2RestTemplate")
    // private ServiceOAuth2RestTemplate template;

    // @Bean("cisSsoLogoutfilterRegistrationBean")
    // public FilterRegistrationBean filterRegistrationBean() {
    //     SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    //     securityContextLogoutHandler.setInvalidateHttpSession(true);
    //
    //     SsoLogoutFilter logoutFilter = new SsoLogoutFilter("/", logOutUri, template, securityContextLogoutHandler)
    //
    // }

}
