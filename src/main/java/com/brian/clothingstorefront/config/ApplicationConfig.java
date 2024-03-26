package com.brian.clothingstorefront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Carries the beans that we want to be propagated throughout the program.
 */
@Configuration
public class ApplicationConfig {
    /**
     * Used only in the initialization when trying to prepare our database for the salted passwords.
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


}
