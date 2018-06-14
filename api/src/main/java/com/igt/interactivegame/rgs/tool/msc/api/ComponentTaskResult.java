package com.igt.interactivegame.rgs.tool.msc.api;

public class ComponentTaskResult {
    public enum Status {
        SUCCESS,
        FAIL
    }

    private final Status status;
    private final IComponent component;
    private final long timeTake;
    private final String message;

    public ComponentTaskResult(Status status, IComponent component, long timeTake, String message) {
        this.status = status;
        this.component = component;
        this.timeTake = timeTake;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public IComponent getComponent() {
        return component;
    }

    public long getTimeTake() {
        return timeTake;
    }

    public String getMessage() {
        return message;
    }
}
