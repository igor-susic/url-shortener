package com.demo.urlshortener.models.resource;

public class CreatingAccountSucceeded extends AccountResponse {

    private String password;

    public CreatingAccountSucceeded(String accountId, String password) {
        this.setSuccess(true);
        this.setDescription(String.format("Your account is successfully opened"));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
