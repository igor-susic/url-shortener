package com.demo.urlshortener.models.resource;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterUrlRequest {

    /**
     * In database we use TEXT type, as we don't know how many characters would URL have.
     * https://stackoverflow.com/questions/417142/what-is-the-maximum-length-of-a-url-in-different-browsers
     */
    @NotEmpty(message = "URL cannot be missing or empty")
    @URL
    private CharSequence url;
    private int redirectType = 302;

    public String getUrl() {
        return url.toString();
    }

    public int getRedirectType() {
        return redirectType;
    }

    public void setUrl(CharSequence url) {
        this.url = url;
    }

    public void setRedirectType(int redirectType) {
        this.redirectType = redirectType;
    }
}
