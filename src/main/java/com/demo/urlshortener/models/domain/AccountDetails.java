package com.demo.urlshortener.models.domain;

import java.util.HashMap;

public class AccountDetails {

    private String id;
    private HashMap<String, Integer> statisticForSubmittedUrls;

    public AccountDetails(String id, HashMap<String, Integer> statisticForSubmittedUrls) {
        this.id = id;
        this.statisticForSubmittedUrls = statisticForSubmittedUrls;
    }

    public HashMap<String, Integer> getStatisticForSubmittedUrls() {
        return statisticForSubmittedUrls;
    }

    public String getId() {
        return id;
    }
}
