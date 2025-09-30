// src/main/java/com/example/sassfnb/service/MenuService.java
package com.example.sassfnb.service;

import com.example.sassfnb.dto.request.*;
import com.example.sassfnb.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuService {
    // categories
    Page<CategoryResponse> listCategories(Pageable pageable);

    CategoryResponse createCategory(CategoryCreateRequest req);

    CategoryResponse updateCategory(String id, CategoryUpdateRequest req);

    void deleteCategory(String id);

    // menu items
    Page<MenuResponse> listMenus(String categoryId, Pageable pageable);

    MenuResponse createMenu(MenuCreateRequest req);

    MenuResponse detailMenu(String id);

    MenuResponse updateMenu(String id, MenuUpdateRequest req);

    void deleteMenu(String id);
}
