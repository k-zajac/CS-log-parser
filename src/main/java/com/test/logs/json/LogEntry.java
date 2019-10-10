package com.test.logs.json;

import java.util.Objects;

public class LogEntry {
    private String id;
    private String state;
    private long timestamp;
    private String type;
    private String host;

    public LogEntry(){

    }

    public LogEntry(String id, String state, long timestamp) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.type = null;
        this.host = null;
    }

    public LogEntry(String id, String state, long timestamp, String type, String host) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
    }

    public String getId() {
        return id;
    }


    public String getState() {
        return state;
    }


    public long getTimestamp() {
        return timestamp;
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
        return timestamp == logEntry.timestamp &&
                id.equals(logEntry.id) &&
                state.equals(logEntry.state) &&
                Objects.equals(type, logEntry.type) &&
                Objects.equals(host, logEntry.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, timestamp, type, host);
    }
}
