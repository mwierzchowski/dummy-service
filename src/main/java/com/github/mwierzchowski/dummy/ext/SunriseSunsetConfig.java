package com.github.mwierzchowski.dummy.ext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.sunrisesunset.api.SunriseSunsetApi;
import org.sunrisesunset.invoker.ApiClient;

@Configuration
public class SunriseSunsetConfig {
    @Bean
    ApiClient sunriseSunsetApiClient(Environment env) {
        var path = env.getRequiredProperty("dummy.sunrise-sunset.path");
        return new ApiClient(new RestTemplate())
                .setBasePath(path);
    }

    @Bean
    SunriseSunsetApi sunriseSunsetApi(ApiClient apiClient) {
        return new SunriseSunsetApi(apiClient);
    }
}
