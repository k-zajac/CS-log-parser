package com.test.logs.service;

import com.test.logs.database.EventDAO;
import com.test.logs.json.LogEntry;
import com.test.logs.model.Event;
import com.test.logs.model.EventBuilder;
import com.test.logs.model.EventKey;

import java.util.Map;

public class AbstractEventService implements EventService {

    protected Map<EventKey, LogEntry> eventMap;
    protected EventDAO eventDAO;

    public AbstractEventService(EventDAO eventDAO){
        this.eventDAO = eventDAO;
    }

    @Override
    public void processLogEntry(LogEntry entry) {
        EventKey key = EventKey.createValidObject(entry.getId(), entry.getState());
        EventKey complementaryKey = key.createComplementaryKey();
        LogEntry complementaryLog = eventMap.get(complementaryKey);

        if(complementaryLog != null){
            Event event = key.getState() == EventKey.StateEnum.STARTED ? EventBuilder.createEvent(entry, complementaryLog) :
                    EventBuilder.createEvent(complementaryLog, entry);
            eventMap.remove(complementaryKey);
            eventDAO.insert(event);
        } else {
            eventMap.put(key, entry);
        }
    }

}
