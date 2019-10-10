package com.test.logs.service;

import com.test.logs.database.EventDAO;

import java.util.HashMap;

public class SingleThreadEventService extends AbstractEventService {
    public SingleThreadEventService(EventDAO eventDAO){
        super(eventDAO);
        eventMap = new HashMap<>();
    }
}
