package com.farah.pfa2024.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Marks this class as a configuration class, indicating that it contains bean definitions.
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //Configures CORS settings for all endpoints (/**).
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedOrigins("*"); //Allows requests from any origin (*).
            }
            /*The addCorsMappings method within the WebMvcConfigurer implementation allows all HTTP methods and all origins for all endpoints,
             ensuring that cross-origin requests are permitted.
             */
        };
    }
}
