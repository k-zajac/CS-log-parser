package com.test.logs.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.JsonException;
import com.test.logs.service.EventService;
import com.test.logs.utils.Metrics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class AbstractJSONParser implements JSONParser{

    private static final Logger LOGGER = LogManager.getLogger();

    private final EventService eventService;
    private final int numberOfThreads;
    private Metrics metrics = new Metrics();

    public AbstractJSONParser(EventService eventService, int numberOfThreads){
        this.eventService = eventService;
        this.numberOfThreads = numberOfThreads;
    }

    public void process(File logFile) {
        try {
            Stream<String> lineStream = Files.lines(logFile.toPath());
            // parallelize stream with given size of threads
            if (1 < numberOfThreads) {
                LOGGER.info("processing file with {%d} threads", numberOfThreads);
                ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfThreads);
                try {
                    forkJoinPool.submit(() -> processLineStream(lineStream.parallel())).get();

                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error(e);
                } finally {
                    forkJoinPool.shutdown();
                }

            } else {
                processLineStream(lineStream);
            }
        } catch (IOException e){
            LOGGER.error(e);
        }
    }

    private void processLineStream(Stream<String> lines){
        lines.forEach(line -> {
            LOGGER.debug("processing line: "+ line);
            metrics.incrementTotal();
            try {
                LogEntry entry = parse(line);
                LOGGER.debug("parsed entry id: "+entry.getId()+" timestamp: "+entry.getTimestamp());
                eventService.processLogEntry(entry);
            }catch(JsonException e){
                LOGGER.warn("failed to parse line "+line);
                metrics.incrementErrors();
            }
        });
    }

    public Metrics getMetrics(){
        return metrics;
    }

    protected LogEntry parse(String line){
        return JsonIterator.deserialize(line, LogEntry.class);
    }

}
