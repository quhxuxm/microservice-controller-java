package com.igt.interactivegame.rgs.tool.msc.impl;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.build.BasicBuildAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.build.configuration.BasicBuildConfigurationProperties;
import com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.BasicDeployApacheAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.BasicDeployTomcatAction;
import com.igt.interactivegame.rgs.tool.msc.impl.action.deploy.configuration.BasicDeployConfigurationProperties;
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
    IComponent __default__() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("Default Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.gsrP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.rgsBuildConfigurationProperties()));
        actions.put(IComponentAction.ActionName.DEPLOY_APACHE,
                this.createDeployApacheAction(result, this.gsrDeployConfigurationProperties()));
        actions.put(IComponentAction.ActionName.DEPLOY_TOMCAT,
                this.createDeployTomcatAction(result, this.gsrDeployConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent rgs() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("RGS Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.rgsP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.gsrBuildConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent gsr() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("GSR Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.gsrP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.rgsBuildConfigurationProperties()));
        actions.put(IComponentAction.ActionName.DEPLOY_APACHE,
                this.createDeployApacheAction(result, this.gsrDeployConfigurationProperties()));
        actions.put(IComponentAction.ActionName.DEPLOY_TOMCAT,
                this.createDeployTomcatAction(result, this.gsrDeployConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent nss() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("NSS Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.nssP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.nssBuildConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent cache() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("Cache Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.cacheP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.cacheBuildConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent common() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("Common Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.commonP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.commonBuildConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent ipl() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("IPL Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.iplP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.iplBuildConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent rgsenv() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("RGS Env Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.rgsenvP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.rgsenvBuildConfigurationProperties()));
        return result;
    }

    @Bean
    IComponent iplenv() {
        Map<IComponentAction.ActionName, IComponentAction> actions = new HashMap<>();
        IComponent result = new BasicComponent("IPL Env Component", actions);
        actions.put(IComponentAction.ActionName.P4_FETCH,
                this.createP4FetchAction(result, this.iplenvP4ConfigurationProperties()));
        actions.put(IComponentAction.ActionName.BUILD,
                this.createBuildAction(result, this.iplenvBuildConfigurationProperties()));
        return result;
    }

    @Bean
    @ConfigurationProperties(prefix = "component.${name}.p4")
    P4ConfigurationProperties defaultP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.rgs.p4")
    P4ConfigurationProperties rgsP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.rgsenv.p4")
    P4ConfigurationProperties rgsenvP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.iplenv.p4")
    P4ConfigurationProperties iplenvP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.gsrenv.p4")
    P4ConfigurationProperties gsrenvP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.ipl.p4")
    P4ConfigurationProperties iplP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.nss.p4")
    P4ConfigurationProperties nssP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.gsr.p4")
    P4ConfigurationProperties gsrP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.cache.p4")
    P4ConfigurationProperties cacheP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.common.p4")
    P4ConfigurationProperties commonP4ConfigurationProperties() {
        return new P4ConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.gsr.build")
    BasicBuildConfigurationProperties gsrBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.ipl.build")
    BasicBuildConfigurationProperties iplBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.rgsenv.build")
    BasicBuildConfigurationProperties rgsenvBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.iplenv.build")
    BasicBuildConfigurationProperties iplenvBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.gsr.deploy")
    BasicDeployConfigurationProperties gsrDeployConfigurationProperties() {
        return new BasicDeployConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.rgs.build")
    BasicBuildConfigurationProperties rgsBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.nss.build")
    BasicBuildConfigurationProperties nssBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.cache.build")
    BasicBuildConfigurationProperties cacheBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "component.common.build")
    BasicBuildConfigurationProperties commonBuildConfigurationProperties() {
        return new BasicBuildConfigurationProperties();
    }

    private BasicP4FetchAction createP4FetchAction(IComponent component,
            P4ConfigurationProperties p4ConfigurationProperties) {
        return new BasicP4FetchAction(component, p4ConfigurationProperties);
    }

    private BasicBuildAction createBuildAction(IComponent component,
            BasicBuildConfigurationProperties basicBuildConfigurationProperties) {
        return new BasicBuildAction(component, basicBuildConfigurationProperties);
    }

    private BasicDeployApacheAction createDeployApacheAction(IComponent component,
            BasicDeployConfigurationProperties basicDeployConfigurationProperties) {
        return new BasicDeployApacheAction(component, basicDeployConfigurationProperties);
    }

    private BasicDeployTomcatAction createDeployTomcatAction(IComponent component,
            BasicDeployConfigurationProperties basicDeployConfigurationProperties) {
        return new BasicDeployTomcatAction(component, basicDeployConfigurationProperties);
    }
}
