package com.example.sassfnb.dto.response;

import com.example.sassfnb.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class UserResponse {
    private String id;
    private String rootId;
    private String name;
    private String email;
    private String phone;
    private User.Role role;
    private Boolean active;
    private Instant createdAt;
}
