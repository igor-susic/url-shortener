package com.demo.urlshortener.services.impl;
import com.demo.urlshortener.entities.Account;
import com.demo.urlshortener.entities.Role;
import com.demo.urlshortener.entities.URL;
import com.demo.urlshortener.models.PairReturnType;
import com.demo.urlshortener.repositories.AccountRepository;
import com.demo.urlshortener.repositories.URLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UrlServiceImplTest {

    @MockBean
    private URLRepository urlRepository;

    @MockBean
    private AccountRepository accountRepository;

    private UrlServiceImpl urlService;
    private Account globalUserForTesting;

    @BeforeEach
    void initUrlService() {
        urlService = new UrlServiceImpl(urlRepository, accountRepository);

        when(accountRepository.save(any(Account.class))).then(returnsFirstArg());
        when(urlRepository.save(any(URL.class))).then(returnsFirstArg());
        doAnswer(invocationOnMock -> {
            URL toSave = (URL) invocationOnMock.getArgument(0);
            ReflectionTestUtils.setField(toSave, "id", 1);

            return toSave;
        }).when(urlRepository).save(any(URL.class));

        globalUserForTesting = new Account("testUser", "password", new Role("ROLE_USER"));
        when(accountRepository.findById("testUser")).thenReturn(Optional.of(globalUserForTesting));
    }

    @Test
    public void shorteningUrlShouldReturnEncodedVersion() {
        String shortenUrl = urlService.shorten("http://google.com/", 301, "testUser");

        assertEquals("b", shortenUrl);
    }

    @Test
    public void shorteningUrlShouldSaveTheUrlEntity() {
        urlService.shorten("http://google.com/", 301, "testUser");

        urlRepository.findAllLinksForGivenAccount(globalUserForTesting).forEach(url -> {
            assertEquals("http://google.com/", url.getOriginalUrl());
            assertEquals(301, url.getRedirectType());
            assertEquals("b", url.getEncodedUrl());
            assertEquals(globalUserForTesting, url.getAccountId());
        });
    }

    @Test
    public void getDestinationAndRedirectTypeShouldReturnValidPairReturnType() {
        URL url = dataProviderForUrl();

        when(urlRepository.findById(1)).thenReturn(Optional.of(url));

        PairReturnType actual = urlService.getDestinationAndRedirectType("b");
        PairReturnType expected = new PairReturnType(301, "https://google.com" ,true);

        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getRedirectType(), actual.getRedirectType());
        assertEquals(expected.isValid(), actual.isValid());
    }

    @Test
    public void registerVisitForUrlShouldUpdateVisitCounter() {
        URL url = dataProviderForUrl();

        when(urlRepository.findById(1)).thenReturn(Optional.of(url));
        when(urlRepository.save(any(URL.class))).then(returnsFirstArg());

        urlService.registerVisitForUrl("b");

        URL updatedUrl = urlRepository.findById(1).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        assertEquals(6, updatedUrl.getNumberOfClicks());
    }

    @Test
    public void getUrlStatisticsForAccountShouldReturnStats() {
        URL url = dataProviderForUrl();

        List<URL> listOfUrls = new ArrayList<>();
        listOfUrls.add(url);

        when(urlRepository.findAllLinksForGivenAccount(any(Account.class))).thenReturn(listOfUrls);

        HashMap<String, Integer> stats = urlService.getUrlStatisticsForAccount(globalUserForTesting);

        stats.forEach((key, value) -> {
            assertEquals("https://google.com", key);
            assertEquals(Integer.valueOf(5), value);
        });
    }

    private URL dataProviderForUrl() {
        URL url = new URL();
        url.setEncodedUrl("b");
        url.setRedirectType(301);
        url.setOriginalUrl("https://google.com");
        url.setNumberOfClicks(5);

        return url;
    }
}
