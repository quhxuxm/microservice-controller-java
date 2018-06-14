package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Configure {
    @Bean
    public Engine engine(ExecutorService executorService, Map<String, IComponent> components) {
        return new Engine(executorService, components);
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    Map<String, IComponent> components() {
        return new HashMap<>();
    }
}
