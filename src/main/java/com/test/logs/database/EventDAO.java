package com.test.logs.database;

import com.test.logs.model.Event;

public interface EventDAO {
    void insert(Event event);
}
