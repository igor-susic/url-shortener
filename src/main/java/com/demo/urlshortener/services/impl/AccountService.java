package com.demo.urlshortener.services.impl;

import com.demo.urlshortener.entities.Account;
import com.demo.urlshortener.entities.Role;
import com.demo.urlshortener.models.domain.AccountDetails;
import com.demo.urlshortener.models.domain.AccountDetailsFactory;
import com.demo.urlshortener.repositorys.AccountRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UrlServiceImpl urlService;

    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder, UrlServiceImpl urlService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.urlService = urlService;
    }

    public AccountDetails getAccountDetails(String id) {
        try {
            Account account = repository.findById(id).orElseThrow();
            return AccountDetailsFactory.create(id, urlService.getUrlStatisticsForAccount(account));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public void saveAccount(String accountId, String password) {
        Role role = new Role();
        role.setId(1);
        role.setRole("ROLE_USER");

        Account account = new Account(accountId, passwordEncoder.encode(password), role);

        try {
            repository.save(account);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger("com.demo.urlshortener.services.saveAccount");
            logger.error("Error while saving new Account to database.", e);
        }
    }

    public boolean doesAccountAlreadyExists(String accountId) {
        return repository.existsAccountByAccountId(accountId);
    }
}
