#CS-log-parser
Credit Suisse coding assignment 

##How to build

To generate executable JAR with dependencies command: gradle shadowJar

##How to run

Simplest case is: by running command:

```java -Dlog4j.configurationFile="src/main/resources/log4j.properties" -jar build\libs\CS-log-parser-1.0-SNAPSHOT.jar -wd WORKING_DIRECTORY```

where WORKING_DIRECTORY is a path in which application should look for file with logs.
 
To see usage and all available parameters run:

```java -Dlog4j.configurationFile="src/main/resources/log4j.properties" -jar build\libs\CS-log-parser-1.0-SNAPSHOT.jar -h```
