package com.crhms.security.authorizationserver.security;

import com.crhms.security.authorizationserver.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/10 16:35
 */

@Service
@Primary
public class UserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUserName("admin");
        user.setUserPassword("123");

        if (username.equals(user.getUsername())) {
            return user;
        }

        throw new UsernameNotFoundException("用户名不存在");
    }
}
