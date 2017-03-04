package com.miskevich.io;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.assertEquals;

public class LogAnalyzerTest {

    private final String PATH = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "LogTest.txt";
    private Collection<LogToken> expectedTokenCollection;

    @BeforeClass
    public void initializeTestData(){
        expectedTokenCollection = new ArrayList<LogToken>(){{
            add(new LogToken(LocalDateTime.of(2004, Month.MARCH, 7, 16, 30, 29), LogToken.HttpMethod.GET, "/twiki/bin/attach/Main/OfficeLocations HTTP/1.1\" 401 12851"));
            add(new LogToken(LocalDateTime.of(2004, Month.MARCH, 7, 16, 31, 48), LogToken.HttpMethod.POST, "/twiki/bin/view/TWiki/WebTopicEditTemplate HTTP/1.1\" 200 3732"));
            add(new LogToken(LocalDateTime.of(2004, Month.MARCH, 7, 16, 32, 50), LogToken.HttpMethod.GET, "/twiki/bin/view/Main/WebChanges HTTP/1.1\" 200 40520"));
            add(new LogToken(LocalDateTime.of(2004, Month.MARCH, 7, 16, 33, 53), LogToken.HttpMethod.POST, "/twiki/bin/edit/Main/Smtpd_etrn_restrictions?topicparent=Main.ConfigurationVariables HTTP/1.1\" 401 12851"));
            add(new LogToken(LocalDateTime.of(2004, Month.MARCH, 7, 16, 35, 19), LogToken.HttpMethod.GET, "/mailman/listinfo/business HTTP/1.1\" 200 6379"));
        }};
    }

    @Test
    public void testScanLog(){
        LocalDateTime timeFrom = LocalDateTime.of(2004, Month.MARCH, 7, 16, 29, 16);
        LocalDateTime timeTo = LocalDateTime.of(2004, Month.MARCH, 7, 16, 36, 22);
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        Collection<LogToken> tokenCollection = logAnalyzer.scanLog(PATH, timeFrom, timeTo);
        assertEquals(tokenCollection, expectedTokenCollection);

    }

}