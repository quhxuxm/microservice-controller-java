package com.igt.interactivegame.rgs.tool.msc.impl.action.build;

import com.igt.interactivegame.rgs.tool.msc.api.ComponentTaskResult;
import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;

public class BasicBuildAction implements IComponentAction {
    private IComponent owner;

    public BasicBuildAction(IComponent owner) {
        this.owner = owner;
    }

    @Override
    public ActionName getName() {
        return ActionName.BUILD;
    }

    @Override
    public IComponent getOwner() {
        return this.owner;
    }

    @Override
    public ComponentTaskResult exec() {
        return null;
    }
}
