package com.miskevich.io;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;


public class LogAnalyzer {

    public Collection<LogToken> scanLog(String path, LocalDateTime timeFrom, LocalDateTime timeTo) {
        Collection<LogToken> tokenCollection = new ArrayList<>();

        File file = new File(path);
        try(Reader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader)){
            String line;
            while ((line = bufferedReader.readLine()) != null){


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss");
                LocalDateTime formattedLogDate = LocalDateTime.parse(getLogTime(line), formatter.withLocale(Locale.ENGLISH));

                int logDateAfterTimeFrom = formattedLogDate.compareTo(timeFrom);
                int logDateBeforeTimeFrom = formattedLogDate.compareTo(timeTo);
                if(logDateAfterTimeFrom == 1 && logDateBeforeTimeFrom == -1){
                    LogToken logToken = new LogToken();

                    logToken.setTime(formattedLogDate);

                    String logMethod = getMethod(line);
                    if(logMethod.equals(LogToken.HttpMethod.GET.getValue())){
                        logToken.setMethod(LogToken.HttpMethod.GET);
                    }else if(logMethod.equals(LogToken.HttpMethod.POST.getValue())){
                        logToken.setMethod(LogToken.HttpMethod.POST);
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
