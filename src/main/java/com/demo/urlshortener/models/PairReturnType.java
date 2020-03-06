package com.demo.urlshortener.models;

/**
 * Custom return type, for usage @see URLServiceImpl
 */
public class PairReturnType {
    private final int redirectType;
    private final String url;
    private final boolean isValid;

    public PairReturnType(boolean isValid) { ;
        this.isValid = isValid;

        this.redirectType = -1;
        this.url = "";
    }

    public PairReturnType(int redirectType, String url, boolean isValid) {
        this.redirectType = redirectType;
        this.url = url;
        this.isValid = isValid;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public int getRedirectType() {
        return redirectType;
    }

    public String getUrl() {
        return url;
    }
}
