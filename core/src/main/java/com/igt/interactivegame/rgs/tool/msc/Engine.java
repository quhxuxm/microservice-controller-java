package com.igt.interactivegame.rgs.tool.msc;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentTaskResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

public class Engine {
    public Map<String, IComponent> getComponents() {
        return null;
    }

    public Optional<Future<IComponentTaskResult>> exec(String actionName, IComponent component) {
        IComponentAction action = component.getActions().computeIfPresent(actionName,(actionName,componentAction)->{})
        return null;
    }
}
