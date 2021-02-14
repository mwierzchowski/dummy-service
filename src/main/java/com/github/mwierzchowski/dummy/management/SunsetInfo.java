package com.github.mwierzchowski.dummy.management;

import com.github.mwierzchowski.dummy.core.DummyChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalTime;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class SunsetInfo implements InfoContributor {
    public static final String ROOT_KEY = "todaySunset";
    public static final String NOT_AVAILABLE = "n/a";

    private final Clock clock;
    private LocalTime sunsetTime;

    @Async
    @EventListener
    public synchronized void onSuccess(DummyChecker.SuccessEvent success) {
        sunsetTime = (LocalTime) success.getSource();
    }

    @Async
    @EventListener
    public synchronized void onFailure(DummyChecker.FailureEvent failure) {
        sunsetTime = null;
    }

    @Override
    public synchronized void contribute(Info.Builder builder) {
        Object details = ofNullable(sunsetTime).map(LocalTime::toString).orElse(NOT_AVAILABLE);
        builder.withDetail(ROOT_KEY, details);
    }
}
