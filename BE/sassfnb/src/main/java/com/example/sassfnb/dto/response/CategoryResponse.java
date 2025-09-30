package com.example.sassfnb.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class CategoryResponse {
    private String id;
    private String rootId;
    private String name;
    private Instant createdAt;
}