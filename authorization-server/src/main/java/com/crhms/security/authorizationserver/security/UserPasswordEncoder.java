package com.crhms.security.authorizationserver.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/10 16:47
 */
public class UserPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
