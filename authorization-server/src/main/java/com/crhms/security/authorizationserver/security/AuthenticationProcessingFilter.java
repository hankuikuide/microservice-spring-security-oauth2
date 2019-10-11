package com.crhms.security.authorizationserver.security;

import com.crhms.security.authorizationserver.model.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationProcessingFilter() {
        super();
        super.setPostOnly(false);
        super.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = null;
        try {
            password = getLoginUser(request).getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String userName = null;
        try {
            userName = getLoginUser(request).getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    private LoginUser getLoginUser(HttpServletRequest request) throws Exception {
        String userPWD = request.getParameter("userPWD");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUser user = objectMapper.readValue(userPWD, LoginUser.class);
        Long ts = (Long) request.getSession().getAttribute("LOGIN_PAGE_TS");
        if (ts == null || !user.getTs().equals(ts)) {
            throw new Exception("页面过期");
        }
        return user;
    }
}