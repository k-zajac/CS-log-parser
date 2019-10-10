package com.test.logs.service;

import com.test.logs.json.LogEntry;

public interface EventService {
    void processLogEntry(LogEntry entry);
}
