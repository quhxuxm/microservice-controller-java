package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class Configure {
    @Bean
    public CoreConfiguration coreConfiguration() {
        return new CoreConfiguration();
    }

    @Bean
    public Engine engine(Map<String, IComponent> components) {
        return new Engine(components, this.coreConfiguration());
    }
}
