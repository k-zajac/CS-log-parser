package com.test.logs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.test.logs.json.AbstractJSONParser;
import com.test.logs.json.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    @Parameter(names={"--working-director", "-wd"}, required = true)
    private String workingDirectory;

    @Parameter(names={"--concurrent", "-c"})
    private boolean isConcurrent = false;

    @Parameter(names={"--threads", "-t"})
    private int numberOfThreads = 1;

    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    @Parameter(names = {"--input-file", "-i"})
    private String fileName = "logfile.txt";

    private JSONParser parser;

    public static void main(String[] args){
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        main.run();
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
    }

    private void initializeContext(){
        if(isConcurrent){
            LOGGER.info("Building application context for multiple threads");
            if(numberOfThreads <= 1){
                int cores = getNumberOfCores();
                LOGGER.warn("Provided number of threads {%d} is not valid for concurrent solution. "
                        +"Found {%d} cores, setting {%d} threads.", numberOfThreads, cores, 2*cores);
                numberOfThreads = 2*cores;
            }

        }else{
            LOGGER.info("Building application context for single thread");
        }
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
