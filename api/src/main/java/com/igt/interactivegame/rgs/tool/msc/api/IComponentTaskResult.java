package com.igt.interactivegame.rgs.tool.msc.api;

public interface IComponentTaskResult {
    enum Status {
        SUCCESS, FAIL
    }

    Status getStatus();
}
