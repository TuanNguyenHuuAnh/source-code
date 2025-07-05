package vn.com.unit.ep2p.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    private static String REDIS_HOST = "localhost";
    private static int REDIS_PORT = 6379;
    private static int REDIS_TIMEOUT_IN_MINUTES = 10;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(REDIS_HOST);
        redisStandaloneConfiguration.setPort(REDIS_PORT);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration
                .builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMinutes(REDIS_TIMEOUT_IN_MINUTES));
        jedisClientConfiguration.usePooling();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                logger.error("Failure getting from cache: " + cache.getName() + ", exception: " + exception.toString(),
                        exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                logger.error("Failure putting into cache: " + cache.getName() + ", exception: " + exception.toString(),
                        exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                logger.error("Failure evicting from cache: " + cache.getName() + ", exception: " + exception.toString(),
                        exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                logger.error("Failure clearing cache: " + cache.getName() + ", exception: " + exception.toString(),
                        exception);
            }
        };
    }

}