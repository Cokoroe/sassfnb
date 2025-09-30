// src/main/java/com/example/sassfnb/entity/TableEntity.java
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
@Table(name = "tables")
public class TableEntity {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "root_id", nullable = false, length = 36)
    private String rootId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "capacity")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.EMPTY;

    @Column(name = "table_code", unique = true, length = 50)
    private String tableCode; // dùng cho QR

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public enum Status {
        EMPTY, BOOKED, IN_USE
    }
}
