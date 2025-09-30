package com.example.sassfnb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffUpdateRequest {
    private String name;
    private String phone;
    private Boolean active;
}
