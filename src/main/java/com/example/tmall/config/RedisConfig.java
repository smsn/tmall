package com.example.tmall.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        defaultCacheConfig = defaultCacheConfig
                // 设置缓存管理器管理的缓存的默认过期时间
                // .entryTtl(Duration.ofSeconds(defaultExpireTime))
                // 设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
                // 不缓存空值
                // .disableCachingNullValues();

        // redisTemplate.setKeySerializer(stringSerializer);
        // redisTemplate.setHashKeySerializer(stringSerializer);
        // redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);


        // Set<String> cacheNames = new HashSet<>();
        // cacheNames.add(testCacheName);

        // // 对每个缓存空间应用不同的配置
        // Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        // configMap.put(testCacheName, defaultCacheConfig.entryTtl(Duration.ofSeconds(testExpireTime)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig)
                // .initialCacheNames(cacheNames)
                // .withInitialCacheConfigurations(configMap)
                .build();

        return cacheManager;
    }

}