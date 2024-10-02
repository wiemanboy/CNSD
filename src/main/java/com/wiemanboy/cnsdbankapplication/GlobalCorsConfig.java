package com.wiemanboy.cnsdbankapplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Value("${spring.web.cors.allowed-origin-patterns:}")
    private String allowedOrigins;

    @Value("${spring.web.cors.allowed-methods:}")
    private String allowedMethods;

    @Value("${spring.web.cors.allowed-headers:}")
    private String allowedHeaders;

    @Value("${spring.web.cors.allow-credentials:false}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        if (allowedOrigins.isEmpty() || allowedMethods.isEmpty() || allowedHeaders.isEmpty()) {
            return;
        }
        corsRegistry.addMapping("/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods(allowedMethods.split(","))
                .allowedHeaders(allowedHeaders.split(","))
                .allowCredentials(allowCredentials);
    }
}
