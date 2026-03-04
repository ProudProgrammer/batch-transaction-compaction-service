package org.gaborbalazs.filter;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
class RateLimitFilter extends OncePerRequestFilter {

    private static final String TITLE_RATE_LIMIT = "Rate limit exceeded";
    private static final String PROPERTY_RETRY = "retryAfterSeconds";

    private final Bandwidth bandwidth;
    private final Cache<String, Bucket> cache;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        Bucket bucket = Objects.requireNonNull(cache.get(ip, key -> Bucket.builder().addLimit(bandwidth).build()));
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            filterChain.doFilter(request, response);
        } else {
            long waitSeconds = TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill());
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader(HttpHeaders.RETRY_AFTER, String.valueOf(waitSeconds));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(createProblemDetail(waitSeconds)));
        }
    }

    private ProblemDetail createProblemDetail(long waitSeconds) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        problemDetail.setTitle(TITLE_RATE_LIMIT);
        problemDetail.setDetail(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
        problemDetail.setProperty(PROPERTY_RETRY, waitSeconds);
        return problemDetail;
    }
}
