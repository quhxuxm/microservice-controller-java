package com.igt.interactivegame.rgs.tool.msc.api;

public interface IComponentAction {
    IComponent getOwner();

    IComponentTaskResult exec();
}
