package com.xjaxl.user.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class HttpRequestUtils {

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getHeader("Authorization");
        }
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }
}
