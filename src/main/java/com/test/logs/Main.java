package com.test.logs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.test.logs.database.EventDAO;
import com.test.logs.json.AbstractJSONParser;
import com.test.logs.json.JSONParser;
import com.test.logs.service.ConcurrentEventService;
import com.test.logs.service.EventService;
import com.test.logs.service.SingleThreadEventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    @Parameter(names={"--working-director", "-wd"}, required = true)
    private String workingDirectory;

    @Parameter(names={"--concurrent", "-c"}, description = "Use concurrent solution")
    private boolean isConcurrent = false;

    @Parameter(names={"--threads", "-t"}, description = "Number of threads to be used. Works only with -c, --concurrent")
    private int numberOfThreads = 1;

    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    @Parameter(names = {"--input-file", "-i"}, description = "File with logs to be processed")
    private String fileName = "logfile.txt";

    @Parameter(names = {"--output-file", "-o"}, description = "Name of database file to store output")
    private String dbName = "events.db";

    private JSONParser parser;

    public boolean isHelp() {
        return help;
    }

    public static void main(String[] args){
        Main main = new Main();
        JCommander jct = JCommander.newBuilder().addObject(main).build();
        jct.parse(args);
        if (main.isHelp()) {
            jct.usage();
        }else{
            main.run();
        }
    }

    private void run(){
        File logFile = null;
        try {
            File wd = getWorkingDirectory();
            logFile = getLogFile(wd);
        } catch (FileNotFoundException fe){
            System.exit(1);
        }
        initializeContext();
        parser.process(logFile);
        LOGGER.info(parser.getMetrics());
    }

    private void initializeContext(){
        EventService service;
        EventDAO eventDAO = new com.test.logs.database.hsqldb.EventDAO();

        if(isConcurrent){
            LOGGER.info("Building application context for multiple threads");
            if(numberOfThreads <= 1){
                int cores = getNumberOfCores();
                LOGGER.warn("Provided number of threads {%d} is not valid for concurrent solution. "
                        +"Found {%d} cores, setting {%d} threads.", numberOfThreads, cores, 2*cores);
                numberOfThreads = 2*cores;

            }
            service = new ConcurrentEventService(eventDAO, numberOfThreads);
        }else{
            LOGGER.info("Building application context for single thread");
            service = new SingleThreadEventService(eventDAO);
            numberOfThreads = 1;
        }

        parser = new AbstractJSONParser(service, numberOfThreads);
    }

    private int getNumberOfCores(){
        return Runtime.getRuntime().availableProcessors();
    }

    private File getWorkingDirectory() throws FileNotFoundException{
        File wd = new File(workingDirectory);
        if(!wd.exists() || !wd.isDirectory()){
            LOGGER.error("Specified working directory {} does not exit or is not directory", workingDirectory);
            throw new FileNotFoundException(workingDirectory);
        }
        return wd;
    }

    private File getLogFile(File wd) throws FileNotFoundException {
        File logFile = new File(wd, fileName);
        if(!logFile.exists() || !logFile.isFile()){
            LOGGER.error("Specified logfile {} does not exit or is not directory", logFile.getPath());
            throw new FileNotFoundException(logFile.getPath());
        }
        return logFile;
    }
}
