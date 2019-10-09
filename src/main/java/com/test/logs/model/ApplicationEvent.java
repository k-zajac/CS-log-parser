package com.test.logs.model;

public class ApplicationEvent extends Event{

    private final String host;
    private final String type;

    protected ApplicationEvent(String id, long start, long duration, String host, String type) {
        super(id, start, duration);
        this.host = host;
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public String getType() {
        return type;
    }
}
