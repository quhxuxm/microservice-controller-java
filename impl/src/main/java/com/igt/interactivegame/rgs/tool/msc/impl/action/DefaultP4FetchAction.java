package com.igt.interactivegame.rgs.tool.msc.impl.action;

import com.igt.interactivegame.rgs.tool.msc.api.IComponent;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentAction;
import com.igt.interactivegame.rgs.tool.msc.api.IComponentTaskResult;
import com.perforce.p4java.server.IServer;

public class DefaultP4FetchAction implements IComponentAction {
    private IServer p4Server;
    private IComponent owner;

    public DefaultP4FetchAction(IComponent owner) {
        this.owner = owner;
    }

    @Override
    public IComponent getOwner() {
        return this.owner;
    }

    @Override
    public IComponentTaskResult exec() {
        return null;
    }
}
