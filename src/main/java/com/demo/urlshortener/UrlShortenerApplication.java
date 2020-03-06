package com.demo.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * We don't want to use auto configuration for security, custom config at ./config/ApplicationSecurity.java
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
