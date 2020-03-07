package com.demo.urlshortener.repositories;

import com.demo.urlshortener.entities.Account;
import com.demo.urlshortener.entities.URL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface URLRepository extends PagingAndSortingRepository<URL, Integer> {

        @Query(value = "SELECT u FROM URL u WHERE u.accountId = :account_id")
    List<URL> findAllLinksForGivenAccount(@Param("account_id") Account account_id);
}
