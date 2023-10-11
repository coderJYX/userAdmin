package com.xjaxl.user.config.shiro.oauth2;

import com.xjaxl.user.config.shiro.UserNameCredentialsMatcher;
import com.xjaxl.user.entity.dto.user.SysUserDTO;
import com.xjaxl.user.service.UserService;
import com.xjaxl.user.common.utils.SpringContextUtils;
import com.xjaxl.user.common.redis.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


@Component
@Log4j2
public class OAuth2Realm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    //设置匹配器
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new UserNameCredentialsMatcher());
    }


    //指定类型
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }


    /**
     * step1: 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 查询用户信息
        String username = (String) token.getPrincipal();

        if ("登陆过期".equals(username)) {
            throw new RuntimeException("登陆过期");
        }
        RedisUtil redisUtil = (RedisUtil) SpringContextUtils.getBean("redisUtil");
        String userId = String.valueOf(redisUtil.getObj("userId:" + username, 30 * 60));
        SysUserDTO sysUserDTO = userService.getById(userId);
        String name = getName();
        log.info(name);
        //认证信息
        return new SimpleAuthenticationInfo(sysUserDTO, username, getName());
    }

    /**
     * step2: 授权(验证权限时调用)
     */
    @SneakyThrows
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException {
//
//        AuthorityRVO authorityRVO = sysUserService.queryUserPermissions();
//        //为当前用户设置角色和权限
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setStringPermissions(authorityRVO.getPermissions());
//        authorizationInfo.setRoles(authorityRVO.getRoleCodes());
//
//        return authorizationInfo;
        return null;
    }


    /**
     * 指定principalCollection 清除
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
