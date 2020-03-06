package com.demo.urlshortener.repositorys;

import com.demo.urlshortener.entities.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {

    boolean existsAccountByAccountId(String accountId);
}
