package com.test.logs.json;

import com.test.logs.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public abstract class AbstractJSONParser implements JSONParser{

    private static final Logger LOGGER = LogManager.getLogger();

    private final EventService eventService;
    private final int numberOfThreads;

    public AbstractJSONParser(EventService eventService, int numberOfThreads){
        this.eventService = eventService;
        if(numberOfThreads <1){
            this.numberOfThreads = 1;
            LOGGER.warn("Specified number of threads {%d} is smaller then 1. Using ", numberOfThreads);
        }else{
            this.numberOfThreads = numberOfThreads;
        }
    }

    public void process(File logFile){

    }

    protected abstract LogEntry parse(String line);

}
