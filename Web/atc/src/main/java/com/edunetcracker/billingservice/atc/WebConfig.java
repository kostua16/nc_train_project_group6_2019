package com.edunetcracker.billingservice.atc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("forward:/pwa/index.html");
        registry.addViewController("/ssp").setViewName("forward:/pwa/index.html");
        registry.addViewController("/ssp/login").setViewName("forward:/pwa/index.html");
        registry.addViewController("/ssp/bank").setViewName("forward:/pwa/index.html");
        registry.addViewController("/ssp/tariff").setViewName("forward:/pwa/index.html");
        registry.addViewController("/crm").setViewName("forward:/pwa/index.html");
        registry.addViewController("/crm/login").setViewName("forward:/pwa/index.html");
        registry.addViewController("/crm/tariffadmin").setViewName("forward:/pwa/index.html");
        registry.addViewController("/crm/history").setViewName("forward:/pwa/index.html");
        registry.addViewController("/atc").setViewName("forward:/pwa/index.html");
    }
}