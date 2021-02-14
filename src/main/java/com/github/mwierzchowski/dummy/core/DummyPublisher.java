package com.github.mwierzchowski.dummy.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Getter
@Service
@RequiredArgsConstructor
public class DummyPublisher {
    public static final String LOCK = "dummy:publisher";

    private final DummyChecker checker;
    private final RedisTemplate<String, Object> redis;

    @Value("${publisher.init-on-startup}")
    private Boolean initOnStart;

    @Value("${publisher.channel}")
    private String channel;

    @EventListener(classes = ApplicationReadyEvent.class, condition = "@dummyPublisher.initOnStart")
    @Scheduled(cron = "${publisher.cron}")
    @SchedulerLock(name = LOCK)
    public void publishSunset() {
        LOG.info("Sending sunset notification");
        var task = new DummyTask("dummy-publisher", checker.todaySunset());
        redis.convertAndSend(channel, task);
    }
}
