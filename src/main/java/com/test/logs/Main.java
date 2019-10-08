package com.test.logs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

    @Parameter(names={"--working-director", "-wd"}, required = true)
    private String workingDirectory;

    @Parameter(names={"--concurrent", "-c"})
    private boolean concurrent = false;

    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    public static void main(String[] args){
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        main.run();
    }

    private void run(){

    }
}
