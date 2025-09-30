package com.example.sassfnb.controller;

import com.example.sassfnb.dto.request.UserLoginRequest;
import com.example.sassfnb.dto.request.UserRegisterRequest;
import com.example.sassfnb.dto.response.ApiResponse;
import com.example.sassfnb.dto.response.UserResponse;
import com.example.sassfnb.service.AuthService;
import com.example.sassfnb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register-root")
    public ApiResponse<String> registerRoot(@RequestBody @Valid UserRegisterRequest req) {
        return authService.registerRoot(req);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid UserLoginRequest req) {
        return authService.login(req);
    }

    // PRIVATE: ROOT hoặc STAFF (SecurityConfig đã chặn)
    @GetMapping("/me")
    public ApiResponse<UserResponse> me() {
        return ApiResponse.ok(userService.getCurrentUser());
    }
}
