package com.example.sassfnb.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableCreateRequest {
    @NotBlank
    private String name;
    private String location;
    private Integer capacity;
}