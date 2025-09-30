package com.example.sassfnb.repository;

import com.example.sassfnb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findAllByRootId(String rootId, Pageable pageable);

    Optional<User> findByIdAndRootId(String id, String rootId);
}
