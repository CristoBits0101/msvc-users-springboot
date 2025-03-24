package com.cryfirock.msvc.users.msvc_users.config;

/**
 * Dependencies
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.MessageSource;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

// Spring configuration classes that additionally scan packages
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.msvc.users.msvc_users",
        "com.cryfirock.msvc.users.msvc_users.validations"
})
public class AppConfig {

    /**
     * Configure the MessageSource bean to use properties files as message sources
     * 
     * @return messageSource
     */
    @Bean
    MessageSource messageSource() {
        // Create a new instance of ReloadableResourceBundleMessageSource
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Set the locations of the message properties files
        messageSource.setBasename("classpath:config/messages");

        // Set the default encoding to UTF-8
        messageSource.setDefaultEncoding("UTF-8");

        // Enable cache for message properties
        return messageSource;
    }

}
