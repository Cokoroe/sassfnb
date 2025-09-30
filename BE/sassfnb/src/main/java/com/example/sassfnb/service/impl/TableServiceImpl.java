// src/main/java/com/example/sassfnb/service/impl/TableServiceImpl.java
package com.example.sassfnb.service.impl;

import com.example.sassfnb.dto.request.*;
import com.example.sassfnb.dto.response.TableResponse;
import com.example.sassfnb.entity.TableEntity;
import com.example.sassfnb.entity.User;
import com.example.sassfnb.repository.TableRepository;
import com.example.sassfnb.repository.UserRepository;
import com.example.sassfnb.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepo;
    private final UserRepository userRepo;

    private User currentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String email = a == null ? null : a.getName();
        return email == null ? null : userRepo.findByEmail(email).orElse(null);
    }

    private String currentRootId() {
        User u = currentUser();
        if (u == null)
            return null;
        return u.getRootId() == null ? u.getId() : u.getRootId();
    }

    private static TableResponse map(TableEntity t) {
        return TableResponse.builder()
                .id(t.getId()).rootId(t.getRootId()).name(t.getName())
                .location(t.getLocation()).capacity(t.getCapacity())
                .status(t.getStatus()).tableCode(t.getTableCode())
                .createdAt(t.getCreatedAt()).build();
    }

    @Override
    public Page<TableResponse> list(Pageable pageable) {
        return tableRepo.findAllByRootId(currentRootId(), pageable).map(TableServiceImpl::map);
    }

    @Override
    public TableResponse create(TableCreateRequest req) {
        String rid = currentRootId();
        TableEntity t = TableEntity.builder()
                .id(UUID.randomUUID().toString())
                .rootId(rid)
                .name(req.getName())
                .location(req.getLocation())
                .capacity(req.getCapacity())
                .status(TableEntity.Status.EMPTY)
                .tableCode("T-" + UUID.randomUUID().toString().substring(0, 8))
                .build();
        tableRepo.save(t);
        return map(t);
    }

    @Override
    public TableResponse detail(String id) {
        String rid = currentRootId();
        TableEntity t = tableRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bàn"));
        return map(t);
    }

    @Override
    public TableResponse update(String id, TableUpdateRequest req) {
        String rid = currentRootId();
        TableEntity t = tableRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bàn"));
        if (req.getName() != null)
            t.setName(req.getName());
        if (req.getLocation() != null)
            t.setLocation(req.getLocation());
        if (req.getCapacity() != null)
            t.setCapacity(req.getCapacity());
        if (req.getStatus() != null)
            t.setStatus(TableEntity.Status.valueOf(req.getStatus()));
        tableRepo.save(t);
        return map(t);
    }

    @Override
    public void delete(String id) {
        String rid = currentRootId();
        TableEntity t = tableRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bàn"));
        tableRepo.delete(t);
    }
}
