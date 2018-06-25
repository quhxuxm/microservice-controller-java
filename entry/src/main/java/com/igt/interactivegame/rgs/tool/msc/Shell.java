package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashMap;
import java.util.Map;

@ShellComponent("msc-shell")
public class Shell {
    private static final Logger logger = LoggerFactory.getLogger(Shell.class);
    private Engine engine;

    @Autowired
    public Shell(Engine engine) {
        this.engine = engine;
    }

    @ShellMethod("Fetch source code from Perforce.")
    public Map<String, ComponentTaskResult> p4fetch(
            @ShellOption(defaultValue = ShellOption.NULL)
                    String... componentNames) {
        return this.execComponentTaskShell(IComponentAction.ActionName.P4_FETCH, componentNames);
    }

    @ShellMethod("Build the source code of a component")
    public Map<String, ComponentTaskResult> build(
            @ShellOption(defaultValue = ShellOption.NULL)
                    String... componentNames) {
        return this.execComponentTaskShell(IComponentAction.ActionName.BUILD, componentNames);
    }

    @ShellMethod("Deploy the apache of a component")
    public Map<String, ComponentTaskResult> deployApache(
            @ShellOption(defaultValue = ShellOption.NULL)
                    String... componentNames) {
        return this.execComponentTaskShell(IComponentAction.ActionName.DEPLOY_APACHE, componentNames);
    }

    @ShellMethod("Deploy the tomcat of a component")
    public Map<String, ComponentTaskResult> deployTomcat(
            @ShellOption(defaultValue = ShellOption.NULL)
                    String... componentNames) {
        return this.execComponentTaskShell(IComponentAction.ActionName.DEPLOY_TOMCAT, componentNames);
    }

    @ShellMethod("Deploy the source code of a component")
    public Map<String, ComponentTaskResult> deploy(
            @ShellOption(defaultValue = ShellOption.NULL)
                    String... componentNames) {
        return this.execComponentTaskShell(IComponentAction.ActionName.DEPLOY_COMPONENT, componentNames);
    }

    @ShellMethod("Start the GUI")
    public String gui() {
        GUI gui = new GUI(this.engine);
        gui.start();
        return "GUI started.";
    }

    private Map<String, ComponentTaskResult> execComponentTaskShell(IComponentAction.ActionName actionName,
            String[] componentNames) {
        final Map<String, ComponentTaskResult> result = new HashMap<>();
        if (componentNames == null) {
            logger.info("No component identified, will [{}] all components.", actionName);
            Map<String, IComponent> components = this.engine.getComponents();
            components.forEach((componentName, component) -> {
                logger.info("Begin to execute the [{}] task on component [{}].", actionName, componentName);
                invokeEngine(result, actionName, componentName, component);
            });
            return result;
        }
        for (String componentName : componentNames) {
            logger.info("Begin to execute the [{}] task on component [{}].", actionName, componentName);
            IComponent component = this.engine.getComponents().get(componentName);
            if (component == null) {
                logger.warn("Can not [{}] component [{}] because of not exist will use default one.", actionName,
                        componentName);
                component = this.engine.getComponents().get("__default__");
            }
            invokeEngine(result, actionName, componentName, component);
        }
        return result;
    }

    private void invokeEngine(Map<String, ComponentTaskResult> resultContainer, IComponentAction.ActionName actionName,
            String componentName, IComponent component) {
        this.engine.exec(actionName, component).ifPresentOrElse(componentTaskResultFuture -> {
            try {
                ComponentTaskResult componentTaskResult = componentTaskResultFuture.get();
                logger.info("Success to execute build task on component [{}].", componentName);
                resultContainer.put(componentName, componentTaskResult);
            } catch (Exception e) {
                logger.error("Fail to execute build task on component [{}] because of exception.", componentName, e);
            }
        }, () -> {
            logger.error("Can not get build result of component [{}].", component.getName());
        });
    }
}
