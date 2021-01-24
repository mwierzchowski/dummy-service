package com.github.mwierzchowski.dummy;

import com.github.mwierzchowski.dummy.core.DummyPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;

@Slf4j
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
@SpringBootApplication
public class DummyApplication {
	@Bean
	Clock systemClock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	@ConditionalOnProperty(name = "dummy.init-on-startup")
	ApplicationListener<ApplicationReadyEvent> initializer() {
		return event -> {
			LOG.info("Initializing on startup");
			event.getApplicationContext().getBean(DummyPublisher.class).publishSunset();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DummyApplication.class, args);
	}
}
