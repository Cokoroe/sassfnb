// src/main/java/com/example/sassfnb/controller/TableController.java
package com.example.sassfnb.controller;

import com.example.sassfnb.dto.request.*;
import com.example.sassfnb.dto.response.ApiResponse;
import com.example.sassfnb.dto.response.TableResponse;
import com.example.sassfnb.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    // STAFF + ROOT (xem)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROOT','STAFF')")
    public ApiResponse<Page<TableResponse>> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(tableService.list(PageRequest.of(page, size)));
    }

    // ROOT (tạo)
    @PostMapping
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<TableResponse> create(@RequestBody TableCreateRequest req) {
        return ApiResponse.ok("Tạo bàn thành công", tableService.create(req));
    }

    // STAFF + ROOT (xem chi tiết)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT','STAFF')")
    public ApiResponse<TableResponse> detail(@PathVariable String id) {
        return ApiResponse.ok(tableService.detail(id));
    }

    // ROOT (sửa)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<TableResponse> update(@PathVariable String id, @RequestBody TableUpdateRequest req) {
        return ApiResponse.ok("Cập nhật bàn thành công", tableService.update(id, req));
    }

    // ROOT (xoá)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROOT')")
    public ApiResponse<Void> delete(@PathVariable String id) {
        tableService.delete(id);
        return ApiResponse.ok("Xoá bàn thành công", null);
    }
}
