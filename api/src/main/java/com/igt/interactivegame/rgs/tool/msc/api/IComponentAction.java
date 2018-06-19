package com.igt.interactivegame.rgs.tool.msc.api;

public interface IComponentAction {
    enum ActionName {
        P4_FETCH,
        BUILD,
        DEPLOY;
    }

    ActionName getName();

    IComponent getOwner();

    ComponentTaskResult exec();
}
