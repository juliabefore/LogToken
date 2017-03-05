package com.miskevich.io;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import static com.miskevich.io.LogToken.HttpMethod.GET;
import static com.miskevich.io.LogToken.HttpMethod.POST;


public class LogAnalyzer implements ILogAnalyzer{

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss");

    public Collection<LogToken> scanLog(String path, LocalDateTime timeFrom, LocalDateTime timeTo) {
        Collection<LogToken> tokenCollection = new ArrayList<>();

        File file = new File(path);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                LocalDateTime formattedLogDate = LocalDateTime.parse(getLogTime(line), DATE_TIME_FORMATTER.withLocale(Locale.ENGLISH));
                if(inRange(timeFrom, timeTo, formattedLogDate)){
                    LogToken logToken = new LogToken();
                    logToken.setTime(formattedLogDate);

                    String logMethod = getMethod(line);
                    if(logMethod.equals(GET.getValue())){
                        logToken.setMethod(GET);
                    }else if(logMethod.equals(POST.getValue())){
                        logToken.setMethod(POST);
                    }

                    logToken.setMessage(getMessage(line));
                    tokenCollection.add(logToken);
                }
            }

        }catch (IOException e){
                throw new RuntimeException(e);
        }



        return tokenCollection;
    }

    private boolean inRange(LocalDateTime timeFrom, LocalDateTime timeTo, LocalDateTime formattedLogDate) {
        return !formattedLogDate.isBefore(timeFrom) && !formattedLogDate.isAfter(timeTo);
    }

    private String getMessage(String line) {
        String logMessageStart = line.substring(line.indexOf("\""));
        return logMessageStart.substring(logMessageStart.indexOf("/"));
    }

    private String getMethod(String line) {
        String logMethodStart = line.substring(line.indexOf("\"") + 1);
        return logMethodStart.substring(0, logMethodStart.indexOf("/") -1);
    }

    private String getLogTime(String line) {
        return line.substring(line.indexOf("[") + 1, line.indexOf("]") - 6);
    }
}
