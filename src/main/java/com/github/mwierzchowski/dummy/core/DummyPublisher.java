package com.github.mwierzchowski.dummy.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DummyPublisher {
    private final DummyChecker checker;
    private final RedisTemplate<String, Object> redis;

    @Scheduled(cron = "${dummy.publisher.cron}")
    @SchedulerLock(name = "dummy-publisher")
    public void publishSunset() {
        var task = new DummyTask("dummy-publisher", checker.todaySunset());
        redis.convertAndSend("sunset-task", task);
        LOG.info("Sent sunset notification");
    }
}
