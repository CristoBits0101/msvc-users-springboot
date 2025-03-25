package com.cryfirock.msvc.users.msvc_users.configurations;

/**
 * Dependencies
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.MessageSource;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import org.springframework.lang.NonNull;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.HandlerInterceptor;

// Spring configuration classes that additionally scan packages
@Configuration
@ComponentScan(basePackages = {
        "com.cryfirock.msvc.users.msvc_users",
        "com.cryfirock.msvc.users.msvc_users.validations"
})
public class AppConfig implements WebMvcConfigurer {

    /**
     * Attributes
     */
    @Autowired
    @Qualifier("timeInterceptor")
    private HandlerInterceptor timeInterceptor;

    /**
     * Register the interceptor and the routes on which it runs
     * 
     * @param registry interceptor registry
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry
                .addInterceptor(timeInterceptor)
                .addPathPatterns("/api/users");
    }

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
