package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentTaskResult;
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

    public Optional<Future<IComponentTaskResult>> exec(String actionName, IComponent component) {
        logger.info("Begin to execute com.igt.interactivegame.rgs.tool.msc.impl.action [{}] on component [{}].", actionName, component.getName());
        IComponentAction action = component.getActions().get(actionName);
        if (action != null) {
            Future<IComponentTaskResult> resultFuture = executorService.submit(action::exec);
            return Optional.of(resultFuture);
        }
        logger.info("The component do not have the com.igt.interactivegame.rgs.tool.msc.impl.action [{}] registered.", actionName);
        return Optional.empty();
    }
}
