package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateStoreRequest;
import com.keepu.webAPI.dto.response.StoreResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.StoresMapper;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.repository.StoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoresService {

    private final StoresRepository storesRepository;
    private final StoresMapper storesMapper;

    @Transactional
    public StoreResponse createStore(CreateStoreRequest request) {
        Stores store = storesMapper.toStoreEntity(request);
        Stores savedStore = storesRepository.save(store);
        return storesMapper.toStoreResponse(savedStore);
    }

    @Transactional(readOnly = true)
    public StoreResponse getStoreById(Integer id) {
        Stores store = storesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tienda no encontrada"));
        return storesMapper.toStoreResponse(store);
    }
}