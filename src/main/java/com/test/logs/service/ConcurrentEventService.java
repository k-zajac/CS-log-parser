package com.test.logs.service;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentEventService extends AbstractEventService {

    public ConcurrentEventService() {
        super();
        eventMap = new ConcurrentHashMap<>();
    }
}
