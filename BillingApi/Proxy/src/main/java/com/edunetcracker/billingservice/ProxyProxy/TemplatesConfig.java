package com.edunetcracker.billingservice.ProxyProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class TemplatesConfig {

    Logger LOG = LoggerFactory.getLogger(TemplatesConfig.class);

    @Autowired
    ApplicationContext context;

    @PostConstruct
    void postConstruct() throws IOException {
        for (Resource resource : context.getResources("classpath:templates/**/*.html")) {
            LOG.info("{} - {}", resource.getURI(), resource.getFilename());
        }
    }


    private SpringResourceTemplateResolver generateResolver(int order, String prefix ) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(context);
        resolver.setPrefix(prefix);
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setOrder(order);
        resolver.setCacheable(true);
        resolver.setCheckExistence(true);
        return resolver;
    }

    @Bean("defaultTemplateResolver")
    ITemplateResolver defaultTemplateResolver(){
        return generateResolver(0,"classpath:templates/");
    }

    @Bean("templatesFromStaticFolderResolver")
    ITemplateResolver templatesFromStaticFolderResolver(){
        return generateResolver(1,"classpath:resources/");
    }
    @Bean("templatesFromThymeleafFolderResolver")
    ITemplateResolver templatesFromThymeleafFolderResolver(){
        return generateResolver(2,"classpath:thymeleaf/");
    }

}
