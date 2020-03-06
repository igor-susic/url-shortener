package com.demo.urlshortener.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "URLS")
public class URL implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "encoded_url", length = 16)
    private String encodedUrl;

    @NotEmpty
    @NotNull
    @Column(name = "original_url")
    private String originalUrl;

    @Min(0)
    @Column(name = "number_of_clicks")
    private int numberOfClicks;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "account_id")
    private Account accountId;

    @NotNull
    @Column(name = "redirect_type")
    private int redirectType;

    public URL() {}

    public int getId() {
        return id;
    }

    public int getNumberOfClicks() {
        return numberOfClicks;
    }

    public Account getAccountId() {
        return accountId;
    }

    public String getEncodedUrl() {
        return encodedUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public int getRedirectType() {
        return redirectType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public void setNumberOfClicks(int numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }

    public void setEncodedUrl(String encodedUrl) {
        this.encodedUrl = encodedUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setRedirectType(int redirectType) {
        this.redirectType = redirectType;
    }
}
