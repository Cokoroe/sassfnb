// src/main/java/com/example/sassfnb/service/TableService.java
package com.example.sassfnb.service;

import com.example.sassfnb.dto.request.TableCreateRequest;
import com.example.sassfnb.dto.request.TableUpdateRequest;
import com.example.sassfnb.dto.response.TableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TableService {
    Page<TableResponse> list(Pageable pageable);

    TableResponse create(TableCreateRequest req);

    TableResponse detail(String id);

    TableResponse update(String id, TableUpdateRequest req);

    void delete(String id);
}
