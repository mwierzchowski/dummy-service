package com.github.mwierzchowski.dummy.core;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Value("${location.latitude}")
    private Double latitude;

    @Value("${location.longitude}")
    private Double longitude;

    @Cacheable
    @Retry(name = "DummyChecker")
    public LocalTime todaySunset() {
        var today = LocalDate.now(clock).toString();
        LOG.debug("Requesting {} sunset for lat={} and lon={}...", today, latitude, longitude);
        var sunsetTime = api.sunriseSunset(latitude, longitude, today, 0)
                .getResults()
                .getSunset()
                .atZoneSameInstant(clock.getZone())
                .toLocalTime();
        LOG.debug("Today's sunset is {}", sunsetTime);
        return sunsetTime;
    }

    @Scheduled(cron = "${checker.cron}")
    @CacheEvict(allEntries = true)
    public void evictCache() {
        LOG.info("Sunset cache evicted");
    }
}
