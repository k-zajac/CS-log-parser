package com.test.logs.json;

import com.jsoniter.JsonIterator;
import com.test.logs.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public abstract class AbstractJSONParser implements JSONParser{

    private static final Logger LOGGER = LogManager.getLogger();

    private final EventService eventService;

    public AbstractJSONParser(EventService eventService, int numberOfThreads){
        this.eventService = eventService;
    }

    public void process(File logFile) {
        try {
            Stream<String> lineStream = Files.lines(logFile.toPath());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected LogEntry parse(String line){
        return JsonIterator.deserialize(line, LogEntry.class);
    }

}
