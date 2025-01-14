package my.socpms.api.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.socpms.api.config.RateLimitConfig;

public class RateLimitInterceptor implements HandlerInterceptor {
    
    private final RateLimitConfig rateLimitConfig;

    public RateLimitInterceptor(RateLimitConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        
        if (!rateLimitConfig.getBucket().tryConsume(1)) {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            return false;
        }
        return true;
    }
}