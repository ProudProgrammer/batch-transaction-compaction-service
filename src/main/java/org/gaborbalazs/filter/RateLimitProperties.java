package org.gaborbalazs.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "rate-limit")
record RateLimitProperties(
        long capacity,
        long refillTokens,
        Duration refillDuration,
        long cacheMaxSize,
        Duration cacheExpireAfter
) { }
