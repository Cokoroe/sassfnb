package com.example.sassfnb.repository;

import com.example.sassfnb.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category> findAllByRootId(String rootId, Pageable pageable);

    Optional<Category> findByIdAndRootId(String id, String rootId);

    boolean existsByRootIdAndNameIgnoreCase(String rootId, String name);
}
