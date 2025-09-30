package com.example.sassfnb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuCreateRequest {
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String categoryId;
    private String imageUrl;
}