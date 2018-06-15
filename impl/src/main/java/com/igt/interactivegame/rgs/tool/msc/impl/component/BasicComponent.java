package com.igt.interactivegame.rgs.tool.msc.impl.component;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;

import java.util.Map;

public class BasicComponent implements IComponent {
    private Map<IComponentAction.ActionName, IComponentAction> actions;
    private String name;

    public BasicComponent(String name, Map<IComponentAction.ActionName, IComponentAction> actions) {
        this.name = name;
        this.actions = actions;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<IComponentAction.ActionName, IComponentAction> getActions() {
        return this.actions;
    }
}
