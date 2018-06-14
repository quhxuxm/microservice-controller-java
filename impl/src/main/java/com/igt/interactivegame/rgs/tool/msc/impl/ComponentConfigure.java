package com.igt.interactivegame.rgs.tool.msc.impl;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.DefaultP4FetchAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.configuration.P4ClientConfigurationProperties;
import com.igt.interactivegame.rgs.tool.msc.impl.action.p4.configuration.P4ServerConfigurationProperties;
import com.igt.interactivegame.rgs.tool.msc.impl.component.DefaultComponent;
import com.perforce.p4java.server.IServer;
import com.perforce.p4java.server.ServerFactory;
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
        IComponent result = new DefaultComponent("RGS Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH, this.createP4FetchAction(result, this.p4Server(), this.rgsP4ClientConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent gsr() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new DefaultComponent("GSR Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH, this.createP4FetchAction(result, this.p4Server(), this.gsrP4ClientConfigurationProperties()));
        return result;
    }

    @ConfigurationProperties(prefix = "p4.server")
    private P4ServerConfigurationProperties p4ServerConfigurationProperties() {
        return new P4ServerConfigurationProperties();
    }

    @Bean
    IServer p4Server() {
        IServer p4Server = null;
        try {
            p4Server = ServerFactory.getServer(p4ServerConfigurationProperties().getUrl(), null);
        } catch (Exception e) {
            return null;
        }
        p4Server.setUserName(p4ServerConfigurationProperties().getUserName());
        p4Server.setAuthTicket(p4ServerConfigurationProperties().getPassword());
        return p4Server;
    }

    @ConfigurationProperties(prefix = "rgs.p4")
    private P4ClientConfigurationProperties rgsP4ClientConfigurationProperties() {
        return new P4ClientConfigurationProperties();
    }

    @ConfigurationProperties(prefix = "gsr.p4")
    private P4ClientConfigurationProperties gsrP4ClientConfigurationProperties() {
        return new P4ClientConfigurationProperties();
    }

    private DefaultP4FetchAction createP4FetchAction(IComponent component, IServer p4Server, P4ClientConfigurationProperties p4ClientConfigurationProperties) {
        return new DefaultP4FetchAction(component, p4Server, p4ClientConfigurationProperties);
    }
}
