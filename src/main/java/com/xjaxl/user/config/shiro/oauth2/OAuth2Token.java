package com.xjaxl.user.config.shiro.oauth2;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * oauth2令牌
 *
 * @author JYX
 * @version 1.0.0
 * @date 2023-03-23
 */
@Data
public class OAuth2Token implements AuthenticationToken {

    private String username;


    public OAuth2Token(String username) {
        this.username = username;
    }


    @Override
    public Object getPrincipal() {
        return this.username;
    }


    @Override
    public Object getCredentials() {
        return this.username;
    }
}
