package com.demo.urlshortener.services.impl;

import com.demo.urlshortener.entities.Account;
import com.demo.urlshortener.entities.URL;
import com.demo.urlshortener.models.PairReturnType;
import com.demo.urlshortener.models.domain.ShortUrl;
import com.demo.urlshortener.repositories.AccountRepository;
import com.demo.urlshortener.repositories.URLRepository;
import com.demo.urlshortener.services.IUrlService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;

@Service
public class UrlServiceImpl implements IUrlService {

    private final URLRepository urlRepository;
    private final AccountRepository accountRepository;

    UrlServiceImpl(URLRepository urlRepository, AccountRepository accountRepository) {
        this.urlRepository = urlRepository;
        this.accountRepository = accountRepository;
    }

    public void registerVisitForUrl(String uri) {
        int idMappedValue = this.decode(uri);

        URL url = urlRepository.findById(idMappedValue).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        url.setNumberOfClicks(url.getNumberOfClicks() + 1);

        try {
            urlRepository.save(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HashMap<String, Integer> getUrlStatisticsForAccount(Account accountId) {
        List<URL>  urlList = urlRepository.findAllLinksForGivenAccount(accountId);
        HashMap<String, Integer> urlStats = new HashMap<>();

        urlList.forEach(url -> {
            urlStats.put(url.getOriginalUrl(), url.getNumberOfClicks());
        });

        return urlStats;
    }

    public String shorten(String longUrl, int redirectType, String currentUserId) {
        URL newUrl = new URL();
        newUrl.setOriginalUrl(longUrl);
        newUrl.setRedirectType(redirectType);

        Account acc = accountRepository.findById(currentUserId).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        newUrl.setAccountId(acc);

        URL url = urlRepository.save(newUrl);
        String encodedShortUrl = this.encode(url.getId());

        url.setEncodedUrl(encodedShortUrl);

        url = urlRepository.save(url);

        return url.getEncodedUrl();
    }

    public PairReturnType getDestinationAndRedirectType(String uri) {
        int idMappedValue = this.decode(uri);

        try {
            URL original = urlRepository.findById(idMappedValue).orElseThrow(() -> {
                throw new EntityNotFoundException();
            });
            return new PairReturnType(original.getRedirectType(), original.getOriginalUrl(), true);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            return new PairReturnType(false);
        }
    }

    private String encode(int num) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ShortUrl.ALPHABET.charAt(num % ShortUrl.BASE));
            num = num / ShortUrl.BASE;
        }
        return str.toString();
    }

    private int decode(String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = num * ShortUrl.BASE + ShortUrl.ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }
}
