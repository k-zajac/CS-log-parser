package com.test.logs.model;

import com.test.logs.json.LogEntry;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventBuilder {

    protected static final Logger LOGGER = LogManager.getLogger();
    private LogEntry startEntry;
    private LogEntry finishEntry;
    private String type;
    private String host;
    private long duration;

    protected EventBuilder(LogEntry startEntry, LogEntry finishEntry){
        this.startEntry = startEntry;
        this.finishEntry = finishEntry;
    }

    protected EventBuilder computeType(){
        type = computeEventValue(startEntry.getType(), finishEntry.getType());
        return this;
    }

    protected EventBuilder computeHost(){
        host = computeEventValue(startEntry.getHost(), finishEntry.getHost());
        return this;
    }

    protected EventBuilder computeDuration(){
        duration = finishEntry.getTimestamp() - startEntry.getTimestamp();
        if(duration < 0){
            LOGGER.warn("computed duration of event is negative number: {%d}", duration);
        }
        return this;
    }

    protected String computeEventValue(String value1, String value2){
        if(StringUtils.equals(value1, value2)){
            return value1;
        }else{
            if(value1 != null){
                return value1;
            }else{
                return value2;
            }
        }
    }
    protected Event build(){
        if(type != null){
            return new ApplicationEvent(startEntry.getId(), startEntry.getTimestamp(), duration, host, type);
        }else {
            return new Event(startEntry.getId(), startEntry.getTimestamp(), duration);
        }
    }
    public static Event createEvent(LogEntry startEntry, LogEntry finishEntry) {
        return new EventBuilder(startEntry, finishEntry).computeDuration().computeHost().computeType().build();
    }
}
