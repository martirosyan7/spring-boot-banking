package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
    boolean existsByUsername(String username);
}
