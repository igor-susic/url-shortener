package com.demo.urlshortener.models.domain;

import java.util.HashMap;

public class AccountDetailsFactory {

    public static AccountDetails create(String id, HashMap<String, Integer> stats) {
        return new AccountDetails(id, stats);
    }
}
