package com.genesys.raa.agg;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class AppConfig {

    @Bean
    Connection getConnection() {
        return null;
    }

}
