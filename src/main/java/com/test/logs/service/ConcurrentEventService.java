package com.test.logs.service;

import com.test.logs.database.EventDAO;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentEventService extends AbstractEventService {

    private int numberOfThreads;
    public ConcurrentEventService(EventDAO eventDAO, int numberOfThreads) {
        super(eventDAO);
        this.numberOfThreads = numberOfThreads;
        eventMap = new ConcurrentHashMap<>();
    }
}
