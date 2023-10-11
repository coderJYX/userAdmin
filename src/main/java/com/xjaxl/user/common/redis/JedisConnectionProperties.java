package com.xjaxl.user.common.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class JedisConnectionProperties {

    private int database;
    private String host;
    private int port;
    private String password;
    private int timeout;

}
