package com.demo.urlshortener.models.resource;

public class CreatingAccountFailedResponse extends AccountResponse {

    public CreatingAccountFailedResponse(String accountId) {
        this.setSuccess(false);
        this.setDescription(String.format("Account with ID: %s already exists. Please choose unique non existing ID.", accountId));
    }
}
