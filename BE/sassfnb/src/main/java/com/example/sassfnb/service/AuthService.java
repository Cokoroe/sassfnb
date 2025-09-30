package com.example.sassfnb.service;

import com.example.sassfnb.dto.request.UserLoginRequest;
import com.example.sassfnb.dto.request.UserRegisterRequest;
import com.example.sassfnb.dto.response.ApiResponse;

public interface AuthService {
    ApiResponse<String> registerRoot(UserRegisterRequest req);

    ApiResponse<String> login(UserLoginRequest req);
}
