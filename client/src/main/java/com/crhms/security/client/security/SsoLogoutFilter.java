package com.crhms.security.client.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 9:30
 */
@Slf4j
public class SsoLogoutFilter extends LogoutFilter {

    private String logoutApiUri;

    private ServiceOAuth2RestTemplate serviceOAuth2RestTemplate;

    public SsoLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, String logoutUri, ServiceOAuth2RestTemplate oAuth2RestTemplate, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
        this.serviceOAuth2RestTemplate = oAuth2RestTemplate;
        this.setLogoutApiUri(logoutUri);
    }

    public void setLogoutApiUri(String uri) {
        if (uri.lastIndexOf("/") != uri.length()) {
            this.logoutApiUri = uri + "/";
        } else {
            this.logoutApiUri = uri;
        }
    }

    @Override
    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        boolean superRequiresLogout = super.requiresLogout(request, response);
        boolean ssoRequires = true;

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null && auth.isAuthenticated()) {
            if (auth instanceof OAuth2Authentication) {
                OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
                Object details = oAuth2Authentication.getUserAuthentication().getDetails();
            }
        }

        return superRequiresLogout;
    }
}
