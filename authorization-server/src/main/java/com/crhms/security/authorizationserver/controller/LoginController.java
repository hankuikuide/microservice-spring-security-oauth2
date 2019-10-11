package com.crhms.security.authorizationserver.controller;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/10 15:39
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        Long ts = new Date().getTime();
        model.addAttribute("ts", ts);
        request.getSession().setAttribute("LOGIN_PAGE_TS", ts);
        model.addAttribute("title", "单点登录平台");
        AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception != null) {
            String message = exception.getMessage();
            model.addAttribute("errmsg", StringUtils.isBlank(message) ? "登录失败" : message);
            if (ClassUtils.isAssignable(exception.getClass(), CredentialsExpiredException.class)) {
                model.addAttribute("updatePwd", true);
            }
        }

        return "login";
    }

    @GetMapping("/ssoLogout")
    public void ssoLogout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
