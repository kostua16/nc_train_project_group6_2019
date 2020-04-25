package com.edunetcracker.billingservice.BillingDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class TemplatesConfig {

    Logger LOG = LoggerFactory.getLogger(TemplatesConfig.class);

    @Autowired
    SpringResourceTemplateResolver templateResolver;

    @Autowired
    ThymeleafViewResolver viewResolver;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ApplicationContext context;

    @PostConstruct
    void postConstruct() throws IOException {
        for (Resource resource : context.getResources("classpath*:**/*.html")) {
            LOG.info("{} - {}", resource.getURI(), resource.getFilename());
        }
    }
}
