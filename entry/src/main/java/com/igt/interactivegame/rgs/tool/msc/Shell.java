package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentTaskResult;
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

    @ShellMethod(value = "Build components")
    public Map<String, IComponentTaskResult> build(@ShellOption(defaultValue = ShellOption.NULL) String... componentNames) {
        return this.execComponentTaskShell("build", componentNames);
    }

    @ShellMethod(value = "Deploy components")
    public Map<String, IComponentTaskResult> deploy(@ShellOption(defaultValue = ShellOption.NULL) String... componentNames) {
        return this.execComponentTaskShell("deploy", componentNames);
    }

    @ShellMethod(value = "Config components")
    public Map<String, IComponentTaskResult> config(@ShellOption(defaultValue = ShellOption.NULL) String... componentNames) {
        return this.execComponentTaskShell("config", componentNames);
    }

    private Map<String, IComponentTaskResult> execComponentTaskShell(String actionName, String[] componentNames) {
        final Map<String, IComponentTaskResult> result = new HashMap<>();
        if (componentNames == null) {
            logger.info("No component identified, will [{}] all components.", actionName);
            Map<String, IComponent> components = this.engine.getComponents();
            components.forEach((componentName, component) -> {
                logger.info("Begin to execute the [{}] task on component [{}].", actionName, componentName);
                invokeEngine(result, actionName, componentName, component);
            });
            return result;
        }
        for (
                String componentName : componentNames) {
            logger.info("Begin to execute the [{}] task on component [{}].", actionName, componentName);
            IComponent component = this.engine.getComponents().get(componentName);
            if (component == null) {
                logger.error("Can not [{}] component [{}] because of not exist.", actionName, componentName);
                continue;
            }
            invokeEngine(result, actionName, componentName, component);
        }
        return result;
    }

    private void invokeEngine(Map<String, IComponentTaskResult> resultContainer, String actionName, String componentName, IComponent component) {
        this.engine.exec(actionName, component).ifPresentOrElse(componentTaskResultFuture -> {
            try {
                IComponentTaskResult componentTaskResult = componentTaskResultFuture.get();
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
