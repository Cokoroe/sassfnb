package com.example.sassfnb.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class MenuResponse {
    private String id;
    private String rootId;
    private String categoryId;
    private String categoryName;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private Boolean active;
    private Instant createdAt;
}