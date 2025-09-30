// src/main/java/com/example/sassfnb/controller/MenuController.java
package com.example.sassfnb.controller;

import com.example.sassfnb.dto.request.*;
import com.example.sassfnb.dto.response.*;
import com.example.sassfnb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // -------- Categories --------
    @GetMapping("/api/categories")
    @PreAuthorize("hasAnyAuthority('ROOT','STAFF')")
    public ApiResponse<Page<CategoryResponse>> listCategories(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(menuService.listCategories(PageRequest.of(page, size)));
    }

    @PostMapping("/api/categories")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryCreateRequest req) {
        return ApiResponse.ok("Tạo danh mục thành công", menuService.createCategory(req));
    }

    @PutMapping("/api/categories/{id}")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String id,
            @RequestBody CategoryUpdateRequest req) {
        return ApiResponse.ok("Cập nhật danh mục thành công", menuService.updateCategory(id, req));
    }

    @DeleteMapping("/api/categories/{id}")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<Void> deleteCategory(@PathVariable String id) {
        menuService.deleteCategory(id);
        return ApiResponse.ok("Xoá danh mục thành công", null);
    }

    // -------- Menu Items --------
    @GetMapping("/api/menu")
    @PreAuthorize("hasAnyAuthority('ROOT','STAFF')")
    public ApiResponse<Page<MenuResponse>> listMenu(@RequestParam(required = false) String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(menuService.listMenus(categoryId, PageRequest.of(page, size)));
    }

    @PostMapping("/api/menu")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<MenuResponse> createMenu(@RequestBody MenuCreateRequest req) {
        return ApiResponse.ok("Thêm món thành công", menuService.createMenu(req));
    }

    @GetMapping("/api/menu/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT','STAFF')")
    public ApiResponse<MenuResponse> detailMenu(@PathVariable String id) {
        return ApiResponse.ok(menuService.detailMenu(id));
    }

    @PutMapping("/api/menu/{id}")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<MenuResponse> updateMenu(@PathVariable String id, @RequestBody MenuUpdateRequest req) {
        return ApiResponse.ok("Cập nhật món thành công", menuService.updateMenu(id, req));
    }

    @DeleteMapping("/api/menu/{id}")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<Void> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return ApiResponse.ok("Xoá món thành công", null);
    }
}
