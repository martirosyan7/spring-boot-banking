package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.LocalUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocalUserRepository extends CrudRepository<LocalUser, Long> {
    boolean existsByUsername(String username);

    Optional<LocalUser> findByUsername(String username);
}
