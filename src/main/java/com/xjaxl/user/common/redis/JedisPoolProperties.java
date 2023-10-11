package com.xjaxl.user.common.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis.jedis.pool")
@Data
public class JedisPoolProperties {

    private int maxActive;
    private int maxWait;
    private int maxIdle;
    private int minIdle;

}
