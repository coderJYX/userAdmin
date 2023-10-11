package com.xjaxl.user.common.utils;


import com.xjaxl.user.entity.dto.user.SysUserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;


public class ShiroUtils {

    // 散列算法: SHA-256
    private final static String hashAlgorithmNameSHA_256 = "SHA-256";

    private final static int hashIterationsSHA_256 = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmNameSHA_256, password, salt,
                hashIterationsSHA_256).toString();
    }

    // 身份验证
    public static Boolean isLogin() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    // 当前用户退出登陆
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /*用户全量信息*/
    private static SysUserDTO getUserEntity() {
        return (SysUserDTO) SecurityUtils.getSubject().getPrincipal();
    }

    /*获取用户Id*/
    public static String getUserId() {
        return getUserEntity().getId();
    }

    /*获取用户名*/
    public static String getUsername() {
        return getUserEntity().getUsername();

    }
    public static String getUserType() {
        return getUserEntity().getUserType();

    }


}
