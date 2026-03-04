package org.gaborbalazs.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
class RateLimitConfig {

    @Bean
    Cache<String, Bucket> rateLimitCache(RateLimitProperties props) {
        return Caffeine.newBuilder()
                .maximumSize(props.cacheMaxSize())
                .expireAfterAccess(props.cacheExpireAfter())
                .build();
    }

    @Bean
    Bandwidth bandwidth(RateLimitProperties props) {
        return Bandwidth.builder()
                .capacity(props.capacity())
                .refillGreedy(props.refillTokens(), props.refillDuration())
                .build();
    }
}
