// src/main/java/com/example/sassfnb/service/impl/MenuServiceImpl.java
package com.example.sassfnb.service.impl;

import com.example.sassfnb.dto.request.*;
import com.example.sassfnb.dto.response.*;
import com.example.sassfnb.entity.*;
import com.example.sassfnb.repository.*;
import com.example.sassfnb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final MenuItemRepository menuRepo;

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

    private static CategoryResponse map(Category c) {
        return CategoryResponse.builder()
                .id(c.getId()).rootId(c.getRootId())
                .name(c.getName()).createdAt(c.getCreatedAt()).build();
    }

    private static MenuResponse map(MenuItem m) {
        return MenuResponse.builder()
                .id(m.getId()).rootId(m.getRootId())
                .categoryId(m.getCategory().getId())
                .categoryName(m.getCategory().getName())
                .name(m.getName()).price(m.getPrice())
                .imageUrl(m.getImageUrl()).active(m.getActive())
                .createdAt(m.getCreatedAt()).build();
    }

    // Categories
    @Override
    public Page<CategoryResponse> listCategories(Pageable pageable) {
        return categoryRepo.findAllByRootId(currentRootId(), pageable).map(MenuServiceImpl::map);
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest req) {
        String rid = currentRootId();
        if (categoryRepo.existsByRootIdAndNameIgnoreCase(rid, req.getName()))
            throw new IllegalArgumentException("Danh mục đã tồn tại");
        Category c = Category.builder()
                .id(UUID.randomUUID().toString())
                .rootId(rid).name(req.getName()).build();
        categoryRepo.save(c);
        return map(c);
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryUpdateRequest req) {
        String rid = currentRootId();
        Category c = categoryRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
        if (req.getName() != null)
            c.setName(req.getName());
        categoryRepo.save(c);
        return map(c);
    }

    @Override
    public void deleteCategory(String id) {
        String rid = currentRootId();
        Category c = categoryRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
        categoryRepo.delete(c);
    }

    // Menus
    @Override
    public Page<MenuResponse> listMenus(String categoryId, Pageable pageable) {
        String rid = currentRootId();
        if (categoryId != null && !categoryId.isBlank()) {
            Category c = categoryRepo.findByIdAndRootId(categoryId, rid)
                    .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
            return menuRepo.findAllByRootIdAndCategory(rid, c, pageable).map(MenuServiceImpl::map);
        }
        return menuRepo.findAllByRootId(rid, pageable).map(MenuServiceImpl::map);
    }

    @Override
    public MenuResponse createMenu(MenuCreateRequest req) {
        String rid = currentRootId();
        Category c = categoryRepo.findByIdAndRootId(req.getCategoryId(), rid)
                .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
        MenuItem m = MenuItem.builder()
                .id(UUID.randomUUID().toString())
                .rootId(rid).category(c)
                .name(req.getName()).price(req.getPrice())
                .imageUrl(req.getImageUrl()).active(true).build();
        menuRepo.save(m);
        return map(m);
    }

    @Override
    public MenuResponse detailMenu(String id) {
        String rid = currentRootId();
        MenuItem m = menuRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy món"));
        return map(m);
    }

    @Override
    public MenuResponse updateMenu(String id, MenuUpdateRequest req) {
        String rid = currentRootId();
        MenuItem m = menuRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy món"));
        if (req.getName() != null)
            m.setName(req.getName());
        if (req.getPrice() != null)
            m.setPrice(req.getPrice());
        if (req.getImageUrl() != null)
            m.setImageUrl(req.getImageUrl());
        if (req.getActive() != null)
            m.setActive(req.getActive());
        if (req.getCategoryId() != null) {
            Category c = categoryRepo.findByIdAndRootId(req.getCategoryId(), rid)
                    .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));
            m.setCategory(c);
        }
        menuRepo.save(m);
        return map(m);
    }

    @Override
    public void deleteMenu(String id) {
        String rid = currentRootId();
        MenuItem m = menuRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy món"));
        menuRepo.delete(m);
    }
}
