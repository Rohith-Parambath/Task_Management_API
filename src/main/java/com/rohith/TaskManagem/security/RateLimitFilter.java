package com.rohith.TaskManagem.security;

import com.rohith.TaskManagem.exception.RateLimitExceededException;
import com.rohith.TaskManagem.service.JwtService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        Bucket bucket;

        if (path.startsWith("/auth/")) {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null) ip = request.getRemoteAddr();
            bucket = rateLimitService.resolveBucketForIp(ip);
        } else {
            String token = request.getHeader("Authorization");
            String userId = null;
            if (token != null && token.startsWith("Bearer ")) {
                userId = jwtService.extractUserId(token.substring(7));
            }
            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Invalid or missing token\"}");
                return;
            }
            bucket = rateLimitService.resolveBucketForUser(userId);
        }

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            long waitForRefill = bucket.getAvailableTokens() == 0 ? 60 : 0;

            response.setStatus(429); // 429
            response.setContentType("application/json");
            response.setHeader("Retry-After", String.valueOf(waitForRefill));

            String jsonResponse = "{"
                    + "\"timestamp\":\"" + java.time.Instant.now().toString() + "\","
                    + "\"status\":429,"
                    + "\"error\":\"Too Many Requests\","
                    + "\"message\":\"Rate limit exceeded\","
                    + "\"retryAfter\":" + waitForRefill
                    + "}";

            response.getWriter().write(jsonResponse);
        }
    }
}
