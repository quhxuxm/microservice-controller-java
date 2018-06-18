package com.igt.interactivegame.rgs.tool.msc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplicationBuilder applicationBuilder=new SpringApplicationBuilder(Main.class);
        applicationBuilder.headless(false).run(args);
    }
}
