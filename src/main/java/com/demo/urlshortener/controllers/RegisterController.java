package com.demo.urlshortener.controllers;

import com.demo.urlshortener.services.impl.UrlBuilderService;
import com.demo.urlshortener.services.impl.UrlServiceImpl;
import com.demo.urlshortener.models.resource.RegisterUrlRequest;
import com.demo.urlshortener.models.resource.RegisterUrlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private final UrlServiceImpl URLshortenerService;
    private final UrlBuilderService urlBuilderService;

    RegisterController(UrlServiceImpl URLshortenerService, UrlBuilderService urlBuilderService) {
        this.URLshortenerService = URLshortenerService;
        this.urlBuilderService = urlBuilderService;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterUrlRequest request) {

        String shortenedUrl = URLshortenerService.shorten(request.getUrl(), request.getRedirectType());

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUrlResponse(urlBuilderService.buildShortUrl(shortenedUrl)));
    }
}
