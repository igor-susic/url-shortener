package com.demo.urlshortener.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class UrlBuilderService {

    @Value("${environment.url}")
    private String domain;

    @Value("${app.port}")
    private int port;

    /**
     * @param encoded For example uri like: abc
     *
     * @return String, either empty or for example: http://www.google.com/abc
     */
    public String buildShortUrl(String encoded) {
        try {
            URL baseUrl = new URL(domain + ":" + port);
            URL shortUrl = new URL(baseUrl, encoded);
            return shortUrl.toString();
        } catch (MalformedURLException e) {
            //todo
            return "";
        }
    }
}
