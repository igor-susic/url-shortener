package com.demo.urlshortener.models.resource;

public class StatisticsFailedResponse {
    private int status;
    private String description;
    private String path;

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
