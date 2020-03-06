package com.demo.urlshortener.models.resource;

public abstract class AccountResponse {

    private Boolean success;
    private String description;

    public String getDescription() {
        return description;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
