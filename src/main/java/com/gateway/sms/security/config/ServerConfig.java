package com.gateway.sms.security.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Data @Component
@ConfigurationProperties(prefix="environment.secret")
public class ServerConfig {
    @Value("${environment.secret}")
    private String secret;
    @Bean
    public String getSecret(){
        return secret;
    }
}

