package com.test.logs.service;

import java.util.HashMap;

public class SingleThreadEventService extends AbstractEventService {
    public SingleThreadEventService(){
        super();
        eventMap = new HashMap<>();
    }
}
