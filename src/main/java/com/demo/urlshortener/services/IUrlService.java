package com.demo.urlshortener.services;

public interface IUrlService {

    /**
     * Method shortens URL to the size of domain + 6 characters + unique id of the account.
     *
     * @param longURL url in format protocol://server.name.tld/uri where protocol can be missing.
     * @param redirectType 301|302
     *
     * @return Shortened URL ex. www.google.com/this-is-quite-long-uri -> to -> www.t.com/urj3745
     */
    public String shorten(String longURL, int redirectType);
}
