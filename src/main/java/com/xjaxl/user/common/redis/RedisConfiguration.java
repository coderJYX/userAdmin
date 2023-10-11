package com.xjaxl.user.common.redis;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @since: 2021/3/25
 * @description: Redis配置
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfiguration extends CachingConfigurerSupport {

    @Resource
    private JedisPoolProperties jedisPoolProperties;

    @Resource
    private JedisConnectionProperties jedisConnectionProperties;

    /*序列化*/
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
    /*反序列化*/
    private static final GenericJackson2JsonRedisSerializer JACKSON__SERIALIZER = new GenericJackson2JsonRedisSerializer();

    //jedis连接池配置
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(jedisPoolProperties.getMaxIdle());   //连接池中的最大空闲连接
        jedisPoolConfig.setMinIdle(jedisPoolProperties.getMinIdle());   //连接池中的最大空闲连接
        jedisPoolConfig.setMaxTotal(jedisPoolProperties.getMaxActive());  //连接池最大连接数（使用负值表示没有限制）
        jedisPoolConfig.setMaxWaitMillis(jedisPoolProperties.getMaxWait());     //连接池最大阻塞等待时间（使用负值表示没有限制）
        return jedisPoolConfig;
    }

    //jedisConnectionFactory配置
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(jedisConnectionProperties.getDatabase());
        factory.setHostName(jedisConnectionProperties.getHost());
        factory.setPort(jedisConnectionProperties.getPort());
        //此处需要注意，当有非空白密码再设置，否则会以带密码的方式连接
        if (StrUtil.isNotBlank(jedisConnectionProperties.getPassword())) {
            factory.setPassword(jedisConnectionProperties.getPassword());
        }
        factory.setTimeout(jedisConnectionProperties.getTimeout());
        factory.setPoolConfig(jedisPoolConfig);
        return factory;
    }

    // 实例化 RedisTemplate 对象（RedisTemplate是SpringDataRedis中对JedisApi的高度封装）
    // TIPS：但事实证明，RedisTemplate在连接池的回收上做的不是很好，不如直接使用jedis，自己回收连接节约资源
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        // 设置数据存入 redis 的序列化方式
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(STRING_SERIALIZER);
        redisTemplate.setValueSerializer(JACKSON__SERIALIZER);
        redisTemplate.setHashKeySerializer(STRING_SERIALIZER);
        redisTemplate.setHashValueSerializer(JACKSON__SERIALIZER);

        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    // 实例化 HashOperations 对象,可以使用 Hash 类型操作
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    // 实例化 ValueOperations 对象,可以使用 String 操作
    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    // 实例化 ListOperations 对象,可以使用 List 操作
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    // 实例化 SetOperations 对象,可以使用 Set 操作
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    // 实例化 ZSetOperations 对象,可以使用 ZSet 操作
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    //============================ 与springCache的相关配置 ============================
    @Bean
    public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        log.info("cacheManager初始化，使用redisTemplate");
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableKeyPrefix()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(STRING_SERIALIZER))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(JACKSON__SERIALIZER));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory)).cacheDefaults(redisCacheConfiguration).build();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }

    @Bean
    RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        return container;
    }
}
