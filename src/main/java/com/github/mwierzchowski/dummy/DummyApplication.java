package com.github.mwierzchowski.dummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;

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

	public static void main(String[] args) {
		SpringApplication.run(DummyApplication.class, args);
	}
}
