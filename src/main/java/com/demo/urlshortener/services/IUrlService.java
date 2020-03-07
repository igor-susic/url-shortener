package com.demo.urlshortener.services;

import com.demo.urlshortener.entities.Account;
import com.demo.urlshortener.models.PairReturnType;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

public interface IUrlService {

    /**
     * Increases visit counter for specific URL by 1.
     *
     * @param uri represents only encoded string part of the shortUrl
     */
    public void registerVisitForUrl(String uri);

    /**
     * @param uri represents only encoded string part of the shortUrl, for example:
     *              full url is http://localhost:8080/abc and the uri param represents only abc
     *
     * @return Custom class designed to hold values of redirect type for specific URL and URL itself.
     */
    public PairReturnType getDestinationAndRedirectType(String uri);

    /**
     * @param accountId String that matches ID of the account example: xyz
     *
     * @return Returns dictionary type structure, where key represents long URL that user registered through
     * /return endpoint, and value is number of visits (redirects) that the specified URL got.
     */
    public HashMap<String, Integer> getUrlStatisticsForAccount(Account accountId);

    /**
     * Method shortens URL to the size of domain + 6 characters + unique id of the account.
     *
     * @param longURL url in format protocol://server.name.tld/uri where protocol can be missing.
     * @param redirectType 301|302
     * @param currentUserId Id of user who requested URL shortening
     *
     * @return Shortened URL ex. www.google.com/this-is-quite-long-uri -> to -> www.t.com/urj3745
     * @throws EntityNotFoundException (this is not expected as context of this method is called with user logged in)
     */
    public String shorten(String longURL, int redirectType, String currentUserId);
}
