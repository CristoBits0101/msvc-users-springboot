package com.cryfirock.msvc.users.msvc_users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config/messages.properties", encoding = "UTF-8")
public class AppConfig {

}
