package com.igt.interactivegame.rgs.tool.msc;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "core")
public class CoreConfiguration {
    private Integer engineThreadPoolSize;

    public Integer getEngineThreadPoolSize() {
        return engineThreadPoolSize;
    }

    public void setEngineThreadPoolSize(Integer engineThreadPoolSize) {
        this.engineThreadPoolSize = engineThreadPoolSize;
    }
}
