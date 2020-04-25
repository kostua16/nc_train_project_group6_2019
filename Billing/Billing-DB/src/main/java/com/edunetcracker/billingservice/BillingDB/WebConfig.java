package com.edunetcracker.billingservice.BillingDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*").addResourceLocations(
                "classpath:/static/",
                "classpath:/APP-INF/resources/static/",
                "classpath:/META-INF/resources/static/",
                "classpath:/src/main/resources/static/",
                "classpath:/META-INF/resources/webjars/"
        );
    }


//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    @Autowired
//    @Primary
//    public DataSource dataSource(DataSourceProperties properties) {
//        String newUrl = properties.getUrl().replaceAll("postgres:", "postgresql:");
//        if(!newUrl.startsWith("jdbc:")){
//            newUrl = "jdbc:" + newUrl;
//        }
//        if(newUrl.startsWith("jdbc:postgresql://") && newUrl.contains("@")){
//            String urlWithoutProtocol = newUrl.substring(18);
//            String[] split = urlWithoutProtocol.split("@");
//            String[] userPassPair = split[0].split(":");
//            newUrl = "jdbc:postgresql://" + split[1];
//            properties.setUsername(userPassPair[0]);
//            properties.setPassword(userPassPair[1]);
//            System.out.println(newUrl);
//            System.out.println(userPassPair[0]);
//            System.out.println(userPassPair[1]);
//        }
//        properties.setUrl(newUrl);
//        return properties.initializeDataSourceBuilder().build();
//    }

}
