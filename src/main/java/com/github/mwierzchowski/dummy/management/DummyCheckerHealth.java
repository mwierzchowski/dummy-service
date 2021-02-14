package com.github.mwierzchowski.dummy.management;

import com.github.mwierzchowski.dummy.core.DummyChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.time.Instant.ofEpochMilli;
import static java.util.Optional.ofNullable;
import static org.springframework.boot.actuate.health.Health.status;
import static org.springframework.boot.actuate.health.Status.DOWN;
import static org.springframework.boot.actuate.health.Status.UNKNOWN;
import static org.springframework.boot.actuate.health.Status.UP;

@Component
@RequiredArgsConstructor
public class DummyCheckerHealth implements HealthIndicator {
    public static final String LAST_SUCCESS_TIME_KEY = "lastSuccessTime";
    public static final String LAST_FAILURE_TIME_KEY = "lastFailureTime";
    public static final String LAST_FAILURE_MSG_KEY = "lastFailureMsg";
    public static final String SUCCESS_COUNT_KEY = "successCount";
    public static final String FAILURE_COUNT_KEY = "failureCount";
    public static final String NOT_AVAILABLE = "n/a";

    private final DateTimeFormatter formatter;

    private Instant lastSuccessTime;
    private Instant lastFailureTime;
    private Exception lastFailureEx;
    private Long successCount = 0L;
    private Long failureCount = 0L;

    @Async
    @EventListener
    public synchronized void onSuccess(DummyChecker.SuccessEvent success) {
        lastSuccessTime = ofEpochMilli(success.getTimestamp());
        successCount += 1;
    }

    @Async
    @EventListener
    public synchronized void onFailure(DummyChecker.FailureEvent failure) {
        lastFailureTime = ofEpochMilli(failure.getTimestamp());
        lastFailureEx = (Exception) failure.getSource();
        failureCount += 1;
    }

    @Override
    public synchronized Health health() {
        return status(currentStatus())
                .withDetails(buildDetails())
                .build();
    }

    private Status currentStatus() {
        if (lastSuccessTime != null && lastFailureTime != null) {
            return lastSuccessTime.isAfter(lastFailureTime) ? UP : DOWN;
        } else if (lastSuccessTime != null) {
            return UP;
        } else if (lastFailureTime != null) {
            return DOWN;
        } else {
            return UNKNOWN;
        }
    }

    private Map<String, ?> buildDetails() {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put(LAST_SUCCESS_TIME_KEY, ofNullable(lastSuccessTime).map(formatter::format).orElse(NOT_AVAILABLE));
        details.put(LAST_FAILURE_TIME_KEY, ofNullable(lastFailureTime).map(formatter::format).orElse(NOT_AVAILABLE));
        details.put(LAST_FAILURE_MSG_KEY, ofNullable(lastFailureEx).map(Exception::getMessage).orElse(NOT_AVAILABLE));
        details.put(SUCCESS_COUNT_KEY, successCount);
        details.put(FAILURE_COUNT_KEY, failureCount);
        return details;
    }
}
