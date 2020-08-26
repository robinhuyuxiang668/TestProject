package com.hyx.test.entity;


import java.io.Serializable;

public class TestEntity implements Serializable {
    private long time;
    private String message;
    private String documentation_url;

    public TestEntity(long time, String message, String documentation_url) {
        this.time = time;
        this.message = message;
        this.documentation_url = documentation_url;
    }

    public TestEntity() {

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }
}
