package com.github.mwierzchowski.dummy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.sunrisesunset.api.SunriseSunsetApi;
import org.sunrisesunset.invoker.ApiClient;

@Configuration
public class SunriseSunsetConfig {
    @Bean
    ApiClient sunriseSunsetApiClient(@Value("${checker.path}") String path) {
        return new ApiClient(new RestTemplate())
                .setBasePath(path);
    }

    @Bean
    SunriseSunsetApi sunriseSunsetApi(ApiClient apiClient) {
        return new SunriseSunsetApi(apiClient);
    }
}
