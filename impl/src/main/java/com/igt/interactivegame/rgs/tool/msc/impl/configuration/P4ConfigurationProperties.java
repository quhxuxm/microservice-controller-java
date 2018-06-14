package com.igt.interactivegame.rgs.tool.msc.impl.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "p4")
public class P4ConfigurationProperties {
    private String serverUrl;
    private int serverPort;
}
