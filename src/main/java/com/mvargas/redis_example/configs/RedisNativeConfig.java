package com.mvargas.redis_example.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.ConnectionPoolConfig;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisNativeConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${spring.data.redis.jedis.pool.max-active}")
    private Integer maxTotal;

    @Value("${spring.data.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.data.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setPassword(redisPassword);

        final JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(2))
                .usePooling()
                .poolConfig(connectionPoolConfig())
                .build();

        return new JedisConnectionFactory(config, jedisConfig);
    }

    private ConnectionPoolConfig connectionPoolConfig() {
        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
        connectionPoolConfig.setMaxTotal(maxTotal);
        connectionPoolConfig.setMaxIdle(maxIdle);
        connectionPoolConfig.setMinIdle(minIdle);
        return connectionPoolConfig;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       ObjectMapper objectMapper) {

        var serializer = new GenericJacksonJsonRedisSerializer(objectMapper);
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();

        return template;
    }
}
