package com.lomash.mytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MockMailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        // Dummy mail sender for development
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // No SMTP configs needed — prevents crash
        return mailSender;
    }
}
