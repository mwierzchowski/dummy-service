package com.github.mwierzchowski.dummy.core;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sunrisesunset.api.SunriseSunsetApi;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@CacheConfig(cacheNames = {"dummy-checker"})
@RequiredArgsConstructor
public class DummyChecker {
    private final SunriseSunsetApi api;
    private final Clock clock;
    private final Environment env;

    @Cacheable
    @Retry(name = "dummy-checker")
    public LocalTime todaySunset() {
        var today = LocalDate.now(clock).toString();
        var latitude = env.getRequiredProperty("dummy.location.latitude", Double.class);
        var longitude = env.getRequiredProperty("dummy.location.longitude", Double.class);
        LOG.debug("Requesting {} sunset for lat={} and lon={}...", today, latitude, longitude);
        var sunsetTime = api.sunriseSunset(latitude, longitude, today, 0)
                .getResults()
                .getSunset()
                .atZoneSameInstant(clock.getZone())
                .toLocalTime();
        LOG.info("Today's sunset is {}", sunsetTime);
        return sunsetTime;
    }

    @Scheduled(cron = "${dummy.checker.cron}")
    @CacheEvict(allEntries = true)
    public void evictCache() {
        LOG.info("Sunset cache evicted");
    }
}
