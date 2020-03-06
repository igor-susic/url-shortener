package com.demo.urlshortener.controllers;

import com.demo.urlshortener.models.resource.AccountResponse;
import com.demo.urlshortener.models.resource.CreatingAccountFailedResponse;
import com.demo.urlshortener.models.resource.CreatingAccountSucceeded;
import com.demo.urlshortener.models.resource.NewAccountRequest;
import com.demo.urlshortener.services.impl.AccountService;
import com.demo.urlshortener.services.impl.PasswordGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final PasswordGeneratorService passwordGeneratorService;

    AccountController(AccountService accountService, PasswordGeneratorService passwordGeneratorService) {
        this.accountService = accountService;
        this.passwordGeneratorService = passwordGeneratorService;
    }

    @PostMapping(path = "/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> createNewAccount(@Valid @RequestBody NewAccountRequest request) {

        String requestedAccountId = request.getAccountId();

        if (accountService.doesAccountAlreadyExists(requestedAccountId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CreatingAccountFailedResponse(requestedAccountId));
        }

        String password = passwordGeneratorService.generate();
        accountService.saveAccount(requestedAccountId, password);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatingAccountSucceeded(requestedAccountId, password));
    }
}
