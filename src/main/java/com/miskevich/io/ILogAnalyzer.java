package com.miskevich.io;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ILogAnalyzer{

    Collection<LogToken> scanLog(String path, LocalDateTime timeFrom, LocalDateTime timeTo);
}
