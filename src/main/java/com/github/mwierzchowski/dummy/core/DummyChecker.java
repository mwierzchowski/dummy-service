package com.github.mwierzchowski.dummy.core;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sunrisesunset.api.SunriseSunsetApi;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@CacheConfig(cacheNames = DummyChecker.CACHE)
@RequiredArgsConstructor
public class DummyChecker {
    public static final String CACHE = "dummy:checker:cache";

    private final SunriseSunsetApi api;
    private final Clock clock;
    private final ApplicationEventPublisher publisher;

    @Value("${location.latitude}")
    private Double latitude;

    @Value("${location.longitude}")
    private Double longitude;

    @Cacheable
    @Retry(name = "DummyChecker")
    public LocalTime todaySunset() {
        LOG.debug("Requesting sunset for lat={} and lon={}...", latitude, longitude);
        try {
            var today = LocalDate.now(clock).toString();
            var sunsetTime = api.sunriseSunset(latitude, longitude, today, 0)
                    .getResults()
                    .getSunset()
                    .atZoneSameInstant(clock.getZone())
                    .toLocalTime();
            LOG.debug("Today's sunset is {}", sunsetTime);
            publisher.publishEvent(new SuccessEvent(sunsetTime));
            return sunsetTime;
        } catch (Exception ex) {
            publisher.publishEvent(new FailureEvent(ex));
            throw ex;
        }
    }

    @Scheduled(cron = "${checker.cron}")
    @CacheEvict(allEntries = true)
    public void evictCache() {
        LOG.info("Sunset cache evicted");
    }

    public static class SuccessEvent extends ApplicationEvent {
        SuccessEvent(LocalTime sunsetTime) {
            super(sunsetTime);
        }
    }

    public static class FailureEvent extends ApplicationEvent {
        FailureEvent(Exception exception) {
            super(exception);
        }
    }
}
