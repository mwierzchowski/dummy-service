package com.github.mwierzchowski.dummy.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Configuration
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
public class CommonConfig {
    @Bean
    Clock systemClock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    DateTimeFormatter timestampFormatter(Clock clock) {
        return ofPattern("yyyy-MM-dd HH:mm:ss").withZone(clock.getZone());
    }
}
