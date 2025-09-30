package com.example.sassfnb.dto.response;

import com.example.sassfnb.entity.TableEntity;
import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class TableResponse {
    private String id;
    private String rootId;
    private String name;
    private String location;
    private Integer capacity;
    private TableEntity.Status status;
    private String tableCode;
    private Instant createdAt;
}