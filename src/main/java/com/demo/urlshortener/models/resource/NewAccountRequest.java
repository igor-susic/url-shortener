package com.demo.urlshortener.models.resource;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class NewAccountRequest {

    @NotEmpty(message = "Account Id cannot be missing or empty")
    @Size(max = 255, message = "AccountId must be less then or equal to 255 characters")
    private CharSequence accountId;

    public String getAccountId() {
        return accountId.toString();
    }

    public void setAccountId(CharSequence accountId) {
        this.accountId = accountId;
    }
}
