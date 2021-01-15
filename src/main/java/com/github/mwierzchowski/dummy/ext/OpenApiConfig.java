package com.github.mwierzchowski.dummy.ext;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.text.MessageFormat.format;
import static java.util.Collections.singletonList;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    OpenAPI openAPI(Environment env) {
        var appInfo = new Info()
                .title(env.getProperty("info.app.name"))
                .description(env.getProperty("info.app.description"))
                .version(env.getProperty("info.app.version"));
        var devServer = new Server()
                .description("development")
                .url(format("http://localhost:{0}", env.getProperty("server.port")));
        return new OpenAPI().info(appInfo).servers(singletonList(devServer));
    }
}
