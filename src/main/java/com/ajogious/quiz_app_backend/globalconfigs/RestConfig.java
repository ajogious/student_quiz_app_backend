package com.ajogious.quiz_app_backend.globalconfigs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("*") // Allow all origins (update as needed)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
                .allowedHeaders("*") // Allow all headers
                .exposedHeaders("Authorization", "Content-Type") // Expose specific headers
                .allowCredentials(false)
                .maxAge(3600); // Cache preflight requests for 1 hour
    }
}
