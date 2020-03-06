package com.demo.urlshortener.models.resource;

public class RegisterUrlResponse {

    private String shortUrl;

    public RegisterUrlResponse(String url) {
        this.shortUrl = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
