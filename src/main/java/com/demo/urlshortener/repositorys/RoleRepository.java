package com.demo.urlshortener.repositorys;

import com.demo.urlshortener.entities.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, String> {

}
