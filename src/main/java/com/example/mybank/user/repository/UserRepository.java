package com.example.mybank.user.repository;

import com.example.mybank.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByPhones(String phones);

    boolean existsByEmails(String emails);

    List<User> findAll(Specification<User> specification, Pageable pageable);
}