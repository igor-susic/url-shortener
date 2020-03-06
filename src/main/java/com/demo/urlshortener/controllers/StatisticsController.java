package com.demo.urlshortener.controllers;

import com.demo.urlshortener.models.domain.AccountDetails;
import com.demo.urlshortener.models.resource.StatisticsFailedResponse;
import com.demo.urlshortener.services.impl.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class StatisticsController {

    private final AccountService accountService;

    StatisticsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/statistic/{AccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getStatistics(@PathVariable("AccountId") String id, HttpServletRequest request) {
        AccountDetails accountDetails = accountService.getAccountDetails(id);

        if (accountDetails == null) {
            StatisticsFailedResponse statisticsFailedResponse = new StatisticsFailedResponse();
            statisticsFailedResponse.setPath(request.getRequestURI());
            statisticsFailedResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(statisticsFailedResponse);
        }

        return ResponseEntity.ok(accountDetails.getStatisticForSubmittedUrls());
    }
}
