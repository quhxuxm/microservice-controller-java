package com.igt.interactivegame.rgs.tool.msc.impl;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.BasicP4FetchAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.configuration.P4ConfigurationProperties;
import com.igt.interactivegame.rgs.tool.msc.impl.component.BasicComponent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ComponentConfigure {
    @Bean
    IComponent rgs() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("RGS Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.rgsP4ConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent gsr() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("GSR Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.gsrP4ConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent nss() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("NSS Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.nssP4ConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent cache() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("Cache Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.cacheP4ConfigurationProperties()));
        return result;
    }

    @Bean
    @ConfigurationProperties(prefix = "rgs.p4")
    P4ConfigurationProperties rgsP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "nss.p4")
    P4ConfigurationProperties nssP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "gsr.p4")
    P4ConfigurationProperties gsrP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "cache.p4")
    P4ConfigurationProperties cacheP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    private BasicP4FetchAction createP4FetchAction(IComponent component,
            P4ConfigurationProperties p4ConfigurationProperties) {
        return new BasicP4FetchAction(component, p4ConfigurationProperties);
    }
}
