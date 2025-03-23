package com.cryfirock.msvc.users.msvc_users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Configuration class for the application
 * This class is used to define the beans and the packages to be scanned
 */
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.msvc.users.msvc_users",
        "com.cryfirock.msvc.users.msvc_users.validations"
})
public class AppConfig {

    /**
     * This bean is used to load the messages.properties file
     * 
     * @return MessageSource
     */
    @Bean
    MessageSource messageSource() {
        // Create a new instance of ReloadableResourceBundleMessageSource
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Load file: src/main/resources/config/messages.properties
        messageSource.setBasename("classpath:config/messages");

        // The encoding is done using UTF-8
        messageSource.setDefaultEncoding("UTF-8");

        // Return the configured MessageSource
        return messageSource;
    }

}
