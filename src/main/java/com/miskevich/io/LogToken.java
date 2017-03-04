package com.miskevich.io;

import java.time.LocalDateTime;

public class LogToken {

    private LocalDateTime time;
    private HttpMethod method;
    private String message;

    public LogToken(LocalDateTime time, HttpMethod method, String message) {
        this.time = time;
        this.method = method;
        this.message = message;
    }

    public LogToken(){}

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    enum HttpMethod{
        GET("GET"),
        POST("POST");

        private String value;

        HttpMethod(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "LogToken{" +
                "time=" + time +
                ", method=" + method +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogToken logToken = (LogToken) o;

        if (time != null ? !time.equals(logToken.time) : logToken.time != null) return false;
        if (method != logToken.method) return false;
        return message != null ? message.equals(logToken.message) : logToken.message == null;
    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
