package com.github.mwierzchowski.dummy.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DummyLogger {
    public void handleMessage(DummyTask task) {
        LOG.info("Received dummy task: {}", task);
    }
}
