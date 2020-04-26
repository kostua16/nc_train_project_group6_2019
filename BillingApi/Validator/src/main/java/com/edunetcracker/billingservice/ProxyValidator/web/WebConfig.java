package com.edunetcracker.billingservice.ProxyValidator.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.billing.url}")
    private String billingUrl = "http://localhost:8202";


    @Value("${app.proxy.url}")
    private String proxyUrl = "http://localhost:8102";

    @Value("${app.validator.url}")
    private String validatorUrl = "http://localhost:8101";

    @Value("${app.web.url}")
    private String webUrl = "http://localhost:8080";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(billingUrl, proxyUrl, validatorUrl, webUrl);
    }

}
