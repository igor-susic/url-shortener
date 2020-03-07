package com.demo.urlshortener.models.resource;

import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

public class RegisterUrlRequest {

    /**
     * In database we use TEXT type, as we don't know how many characters would URL have.
     * https://stackoverflow.com/questions/417142/what-is-the-maximum-length-of-a-url-in-different-browsers
     */
    @NotEmpty(message = "URL cannot be missing or empty")
    @URL
    private CharSequence url;

    @Range(min = 301, max = 302, message = "Redirect type could only hold 301|302 codes.")
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
