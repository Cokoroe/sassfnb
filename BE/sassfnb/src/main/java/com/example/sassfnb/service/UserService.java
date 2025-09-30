package com.example.sassfnb.service;

import com.example.sassfnb.dto.request.StaffCreateRequest;
import com.example.sassfnb.dto.request.StaffUpdateRequest;
import com.example.sassfnb.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse getCurrentUser();

    UserResponse createStaff(StaffCreateRequest req);

    Page<UserResponse> listStaff(Pageable pageable);

    UserResponse getStaff(String id);

    UserResponse updateStaff(String id, StaffUpdateRequest req);

    void deleteStaff(String id);
}
