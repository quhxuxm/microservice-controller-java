package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Engine {
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);
    private ExecutorService executorService;
    private Map<String, IComponent> components;

    public Engine(ExecutorService executorService, Map<String, IComponent> components) {
        this.executorService = executorService;
        this.components = components;
    }

    public Map<String, IComponent> getComponents() {
        return this.components;
    }

    public Optional<Future<ComponentTaskResult>> exec(IComponentAction.ActionName actionName, IComponent component) {
        logger.info("Begin to execute action [{}] on component [{}].", actionName.name(), component.getName());
        IComponentAction action = component.getActions().get(actionName);
        if (action != null) {
            Future<ComponentTaskResult> resultFuture = executorService.submit(action::exec);
            return Optional.of(resultFuture);
        }
        logger.info("The component do not have the action [{}] registered.", actionName.name());
        return Optional.empty();
    }
}
