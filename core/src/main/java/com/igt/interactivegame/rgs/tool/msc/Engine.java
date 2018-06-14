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

    public Engine(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Map<String, IComponent> getComponents() {
        return null;
    }

    public Optional<Future<IComponentTaskResult>> exec(String actionName, IComponent component) {
        logger.info("Begin to execute action [{}] on component [{}].", actionName, component.getName());
        IComponentAction action = component.getActions().get(actionName);
        if (action != null) {
            Future<IComponentTaskResult> resultFuture = executorService.submit(action::exec);
            return Optional.of(resultFuture);
        }
        return Optional.empty();
    }
}
