package com.test.logs.json;

import java.util.Objects;

public class LogEntry {
    private final String id;
    private final String state;
    private final long duration;
    private final String type;
    private final String host;

    public LogEntry(String id, String state, long duration) {
        this.id = id;
        this.state = state;
        this.duration = duration;
        this.type = null;
        this.host = null;
    }

    public LogEntry(String id, String state, long duration, String type, String host) {
        this.id = id;
        this.state = state;
        this.duration = duration;
        this.type = type;
        this.host = host;
    }

    public String getId() {
        return id;
    }


    public String getState() {
        return state;
    }


    public long getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return duration == logEntry.duration &&
                id.equals(logEntry.id) &&
                state.equals(logEntry.state) &&
                Objects.equals(type, logEntry.type) &&
                Objects.equals(host, logEntry.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, duration, type, host);
    }
}
