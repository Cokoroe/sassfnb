package com.example.sassfnb.repository;

import com.example.sassfnb.entity.MenuItem;
import com.example.sassfnb.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, String> {
    Page<MenuItem> findAllByRootId(String rootId, Pageable pageable);

    Page<MenuItem> findAllByRootIdAndCategory(String rootId, Category category, Pageable pageable);

    Optional<MenuItem> findByIdAndRootId(String id, String rootId);
}
