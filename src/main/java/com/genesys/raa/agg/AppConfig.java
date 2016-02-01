package com.genesys.raa.agg;

import org.springframework.context.annotation.*;

@org.springframework.context.annotation.Configuration
public class AppConfig {

    @Bean
    public Configuration getConfiguration(){
        return new Configuration();
    }
}
