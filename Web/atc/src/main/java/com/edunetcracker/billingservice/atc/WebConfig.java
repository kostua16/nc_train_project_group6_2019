package com.edunetcracker.billingservice.atc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pwa/*").addResourceLocations(
                "classpath:/static/",
                "classpath:/APP-INF/resources/static/",
                "classpath:/META-INF/resources/static/",
                "classpath:/src/main/resources/static/",
                "classpath:/META-INF/resources/webjars/"
        );
    }


}