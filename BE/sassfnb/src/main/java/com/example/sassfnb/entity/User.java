package com.example.sassfnb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id; // <- String

    @Column(name = "root_id", nullable = false, length = 36)
    private String rootId; // <- String

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Column(name = "phone", length = 20)
    private String phone;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true; // @Builder.Default để hết warning

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public enum Role {
        ROOT, STAFF, CUSTOMER, SUPPLIER
    }
}
