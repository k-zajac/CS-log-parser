package com.test.logs.model;

public class Event {
    private final String id;
    private final long start;
    private final long duration;

    public Event(String id, long start, long duration) {
        this.id = id;
        this.start = start;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public long getStart() {
        return start;
    }

    public long getDuration() {
        return duration;
    }
}
