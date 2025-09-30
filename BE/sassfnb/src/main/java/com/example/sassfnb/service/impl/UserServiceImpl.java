package com.example.sassfnb.service.impl;

import com.example.sassfnb.dto.request.StaffCreateRequest;
import com.example.sassfnb.dto.request.StaffUpdateRequest;
import com.example.sassfnb.dto.response.UserResponse;
import com.example.sassfnb.entity.User;
import com.example.sassfnb.repository.UserRepository;
import com.example.sassfnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    private String currentEmail() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return a == null ? null : a.getName();
    }

    private User currentUserEntity() {
        String email = currentEmail();
        return email == null ? null : userRepo.findByEmail(email).orElse(null);
    }

    private static UserResponse map(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .rootId(u.getRootId())
                .name(u.getName())
                .email(u.getEmail())
                .phone(u.getPhone())
                .role(u.getRole())
                .active(u.getActive())
                .createdAt(u.getCreatedAt())
                .build();
    }

    @Override
    public UserResponse getCurrentUser() {
        User u = currentUserEntity();
        return map(u);
    }

    @Override
    public UserResponse createStaff(StaffCreateRequest req) {
        User root = currentUserEntity(); // chỉ ROOT được gọi (đã chặn ở Security)
        if (root == null || root.getRole() != User.Role.ROOT) {
            throw new IllegalStateException("Chỉ ROOT được phép tạo STAFF");
        }
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        String staffId = java.util.UUID.randomUUID().toString();
        String rid = root.getRootId() == null ? root.getId() : root.getRootId();

        User s = User.builder()
                .id(staffId)
                .rootId(rid)
                .name(req.getName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(User.Role.STAFF)
                .active(true)
                .build();

        userRepo.save(s);
        return map(s);
    }

    @Override
    public Page<UserResponse> listStaff(Pageable pageable) {
        User root = currentUserEntity();
        String rid = root.getRootId() == null ? root.getId() : root.getRootId();
        return userRepo.findAllByRootId(rid, pageable).map(UserServiceImpl::map);
    }

    @Override
    public UserResponse getStaff(String id) {
        User root = currentUserEntity();
        String rid = root.getRootId() == null ? root.getId() : root.getRootId();
        User u = userRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy STAFF"));
        return map(u);
    }

    @Override
    public UserResponse updateStaff(String id, StaffUpdateRequest req) {
        User root = currentUserEntity();
        String rid = root.getRootId() == null ? root.getId() : root.getRootId();
        User u = userRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy STAFF"));

        if (req.getName() != null)
            u.setName(req.getName());
        if (req.getPhone() != null)
            u.setPhone(req.getPhone());
        if (req.getActive() != null)
            u.setActive(req.getActive());

        userRepo.save(u);
        return map(u);
    }

    @Override
    public void deleteStaff(String id) {
        User root = currentUserEntity();
        String rid = root.getRootId() == null ? root.getId() : root.getRootId();
        User u = userRepo.findByIdAndRootId(id, rid)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy STAFF"));
        userRepo.delete(u);
    }
}
