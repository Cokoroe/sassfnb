package com.example.sassfnb.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuUpdateRequest {
    private String name;
    private BigDecimal price;
    private String categoryId;
    private String imageUrl;
    private Boolean active;
}