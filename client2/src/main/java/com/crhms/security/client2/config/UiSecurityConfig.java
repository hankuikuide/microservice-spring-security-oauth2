package com.crhms.security.client2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableOAuth2Sso
public class  UiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.oauth2.client.logoutUri}")
    private String logougUri;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //http.authorizeRequests()方法有很多子方法，每个子匹配器将会按照声明的顺序起作用
                .authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("http://localhost:9002/ssoLogout")
                .and()
                .csrf().disable();

        // http
        //         .formLogin().loginPage("/login").permitAll()
        //         .and()
        //         .logout()
        //         .and()
        //         .requestMatchers()
        //         .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access", "/exit")
        //         .and()
        //         .authorizeRequests()
        //         .antMatchers("/webjars/**").permitAll()
        //         .anyRequest().authenticated();

    }

}
