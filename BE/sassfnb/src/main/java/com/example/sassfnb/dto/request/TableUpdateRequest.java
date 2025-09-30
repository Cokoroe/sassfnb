package com.example.sassfnb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableUpdateRequest {
    private String name;
    private String location;
    private Integer capacity;
    private String status; // EMPTY/BOOKED/IN_USE
}