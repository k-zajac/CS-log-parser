package com.test.logs.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public final class EventKey {
    private final String id;
    private final StateEnum state;
    private final int hashCode;

    public enum StateEnum{
        STARTED,
        FINISHED;
        static {
            STARTED.opposite = FINISHED;
            FINISHED.opposite = STARTED;
        }

        public StateEnum getOpposite() {
            return opposite;
        }

        private StateEnum opposite;
    }

    private EventKey(String id, StateEnum state) {
        this.id = id;
        this.state = state;
        this.hashCode = Objects.hash(id, state);
    }

    public String getId() {
        return id;
    }

    public StateEnum getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventKey eventKey = (EventKey) o;
        return Objects.equals(id, eventKey.id) &&
                state == eventKey.state;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public static EventKey createValidObject(String id, String state){
        if(StringUtils.isBlank(id)){
            throw new IllegalArgumentException("EventKey.createValidObject - id can't be blank string");
        }
        StateEnum stateEnum = StateEnum.valueOf(state);
        return new EventKey(id, stateEnum);
    }

    public EventKey createComplementaryKey(){
        return new EventKey(id, state.getOpposite());
    }
}
