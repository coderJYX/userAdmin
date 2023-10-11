package com.xjaxl.user.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;


/**
 * 用户名凭证匹配器
 *
 * @author JYX
 * @version 1.0.0
 * @date 2023-03-23
 */
@Component
public class UserNameCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String username = (String) token.getPrincipal();

        String realUserName = (String) info.getCredentials();

        return username.equalsIgnoreCase(realUserName);
    }

}
