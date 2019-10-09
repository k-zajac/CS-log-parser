package com.test.logs.service;

import com.test.logs.json.LogEntry;
import com.test.logs.model.EventKey;

public interface EventService {
    void processLogEntry(LogEntry entry);
    void findComplementaryEntry(EventKey key);
}
