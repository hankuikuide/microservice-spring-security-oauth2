package com.crhms.security.authorizationserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUser {
    private String username;

    private String password;

    private Long ts;
}
