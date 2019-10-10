package com.test.logs.json;

import com.test.logs.utils.Metrics;

import java.io.File;

public interface JSONParser {
    void process(File logFile);
    Metrics getMetrics();
}
