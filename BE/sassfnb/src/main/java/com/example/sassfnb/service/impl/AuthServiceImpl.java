package com.example.sassfnb.service.impl;

import com.example.sassfnb.dto.request.UserLoginRequest;
import com.example.sassfnb.dto.request.UserRegisterRequest;
import com.example.sassfnb.dto.response.ApiResponse;
import com.example.sassfnb.entity.User;
import com.example.sassfnb.repository.UserRepository;
import com.example.sassfnb.security.JwtTokenProvider;
import com.example.sassfnb.service.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authManager;

    @Override
    @Transactional
    public ApiResponse<String> registerRoot(UserRegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        String rootId = UUID.randomUUID().toString();

        User root = User.builder()
                .id(rootId)
                .rootId(rootId)
                .name(req.getName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(User.Role.ROOT)
                .active(true)
                .build();
        userRepo.save(root);

        // Tạo restaurant gắn với root
        Restaurant r = Restaurant.builder()
                .id(UUID.randomUUID().toString())
                .rootId(rootId)
                .name("Cửa hàng của " + req.getName())
                .address(null)
                .build();
        restaurantRepo.save(r);

        return ApiResponse.ok("Đăng ký ROOT thành công", null);
    }

    @Override
    public ApiResponse<String> login(UserLoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User u = userRepo.findByEmail(req.getEmail()).orElseThrow();

        String token = tokenProvider.generateToken(
                u.getEmail(), u.getRole().name(),
                u.getId(), // String
                u.getRootId() // String
        );
        return ApiResponse.ok("Đăng nhập thành công", token);
    }
}
