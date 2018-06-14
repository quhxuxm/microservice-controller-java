package com.igt.interactivegame.rgs.tool.msc.api;

import java.util.Map;

public interface IComponent {
    String getName();

    Map<IComponentAction.ActionName, IComponentAction> getActions();
}
