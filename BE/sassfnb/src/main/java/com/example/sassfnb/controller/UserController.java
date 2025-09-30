package com.example.sassfnb.controller;

import com.example.sassfnb.dto.request.StaffCreateRequest;
import com.example.sassfnb.dto.request.StaffUpdateRequest;
import com.example.sassfnb.dto.response.ApiResponse;
import com.example.sassfnb.dto.response.UserResponse;
import com.example.sassfnb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<Page<UserResponse>> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(userService.listStaff(PageRequest.of(page, size)));
    }

    @PostMapping
    public ApiResponse<UserResponse> create(@RequestBody @Valid StaffCreateRequest req) {
        return ApiResponse.ok("Tạo STAFF thành công", userService.createStaff(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> detail(@PathVariable String id) {
        return ApiResponse.ok(userService.getStaff(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(@PathVariable String id,
            @RequestBody @Valid StaffUpdateRequest req) {
        return ApiResponse.ok("Cập nhật STAFF thành công", userService.updateStaff(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        userService.deleteStaff(id);
        return ApiResponse.ok("Xoá STAFF thành công", null);
    }
}
