package com.test.logs.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class Metrics {
    AtomicInteger total = new AtomicInteger(0);
    AtomicInteger errors = new AtomicInteger(0);

    public void incrementTotal(){
        total.incrementAndGet();
    }

    public void incrementErrors(){
        errors.incrementAndGet();
    }

    public int getTotal(){
        return total.get();
    }

    public int getErrors(){
        return errors.get();
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "total=" + getTotal() +
                ", errors=" + getErrors() +
                ", successful= " + (getTotal() - getErrors()) +
                '}';
    }
}
