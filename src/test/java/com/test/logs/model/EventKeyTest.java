package com.test.logs.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventKeyTest {

    @Test(expected=IllegalArgumentException.class)
    public void createValidObject_idBeingNull_throwsException(){
        EventKey.createValidObject(null, "STARTED");
    }

    @Test(expected=IllegalArgumentException.class)
    public void createValidObject_idBeingEmpty_throwsException(){
        EventKey.createValidObject("", "STARTED");
    }

    @Test(expected=IllegalArgumentException.class)
    public void createValidObject_idBeingBlank_throwsException(){
        EventKey.createValidObject("    ", "STARTED");
    }

    @Test(expected=NullPointerException.class)
    public void createValidObject_stateBeingNull_throwsException(){
        EventKey.createValidObject("event_id", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createValidObject_stateBeingNotEnumValue_throwsException(){
        EventKey.createValidObject("event_id", "DOING");
    }

    @Test()
    public void createValidObject_idNonBlankAndStateStarted_successfullyCreatesObject(){
        String id = "event_id";

        EventKey key = EventKey.createValidObject(id, "STARTED");

        assertEquals(id, key.getId());
        assertEquals(EventKey.StateEnum.STARTED, key.getState());
    }

    @Test()
    public void createValidObject_idNonBlankAndStateFinished_successfullyCreatesObject(){
        String id = "event_id";

        EventKey key = EventKey.createValidObject(id, "FINISHED");

        assertEquals(id, key.getId());
        assertEquals(EventKey.StateEnum.FINISHED, key.getState());
    }

    @Test()
    public void createComplementaryKey_forEventWithStateStarted_createsObjectWithStateFinishedAndSameId(){
        EventKey key = EventKey.createValidObject("event_id", "STARTED");

        EventKey complementaryKey = key.createComplementaryKey();

        assertEquals(key.getId(), complementaryKey.getId());
        assertEquals(EventKey.StateEnum.FINISHED, complementaryKey.getState());
    }

    @Test()
    public void createComplementaryKey_forEventWithStateFinished_createsObjectWithStateStartedAndSameId(){
        EventKey key = EventKey.createValidObject("event_id", "FINISHED");

        EventKey complementaryKey = key.createComplementaryKey();

        assertEquals(key.getId(), complementaryKey.getId());
        assertEquals(EventKey.StateEnum.STARTED, complementaryKey.getState());
    }
}