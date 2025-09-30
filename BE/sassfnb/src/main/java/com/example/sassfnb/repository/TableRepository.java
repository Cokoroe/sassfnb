package com.example.sassfnb.repository;

import com.example.sassfnb.entity.TableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<TableEntity, String> {
    Page<TableEntity> findAllByRootId(String rootId, Pageable pageable);

    Optional<TableEntity> findByIdAndRootId(String id, String rootId);

    boolean existsByTableCode(String tableCode);
}
