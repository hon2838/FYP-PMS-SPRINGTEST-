package my.socpms.api.config;

import java.time.Duration;

import org.springframework.context.annotation.Configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

@Configuration
public class RateLimitConfig {

    private final Bucket bucket;

    public RateLimitConfig() {
        Bandwidth limit = Bandwidth.builder()
            .capacity(5)
            .refillGreedy(5, Duration.ofMinutes(5))
            .build();
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }

    public Bucket getBucket() {
        return bucket;
    }
}

