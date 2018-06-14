package com.igt.interactivegame.rgs.tool.msc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configure {
    @Bean
    public Engine engine() {
        return new Engine();
    }
}
