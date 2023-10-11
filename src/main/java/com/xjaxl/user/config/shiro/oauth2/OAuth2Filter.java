package com.xjaxl.user.config.shiro.oauth2;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xjaxl.user.common.utils.HttpContextUtils;
import com.xjaxl.user.common.utils.HttpRequestUtils;
import com.xjaxl.user.common.utils.JsonUtils;
import com.xjaxl.user.common.utils.SpringContextUtils;
import com.xjaxl.user.common.redis.RedisUtil;
import com.xjaxl.user.common.result.ResponseEnum;
import com.xjaxl.user.common.result.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author JYX
 * @version 1.0.0
 * @date 2023-03-23
 */
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = HttpRequestUtils.getRequestToken((HttpServletRequest) request);
        //未登录，没有token
        if (StrUtil.isBlank(token)) {
            return new OAuth2Token("登陆过期");
        }
        RedisUtil redisUtil = (RedisUtil) SpringContextUtils.getBean("redisUtil");
        Object userName = redisUtil.getObj("token:" + token, 30 * 60);

        if (ObjectUtil.isEmpty(userName)) {
            return new OAuth2Token("登陆过期");
        }

        //已登录，生成token令牌，要实现免密登录可以再此类中新增构造方法
        return new OAuth2Token(String.valueOf(userName));
    }


    // 判断是否可访问
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        //获取请求token，如果token不存在，直接返回401
        String token = HttpRequestUtils.getRequestToken((HttpServletRequest) request);
        if (StrUtil.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            httpResponse.setHeader("Content-Type","application/json;charset=UTF-8");
            ResultWrapper error = ResultWrapper.error(ResponseEnum.LOGIN_TIMEOUT);
            String json = JsonUtils.objectToJson(error);
            httpResponse.getWriter().print(json);
            return false;
        }

        //token存在，则subject执行login操作进行shiro的登录
        return executeLogin(request, response);
    }


    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            assert throwable instanceof RuntimeException;
            RuntimeException b = (RuntimeException) throwable;
            httpResponse.getWriter().print(b.getMessage());
        } catch (IOException ignored) {

        }

        return false;
    }

}
