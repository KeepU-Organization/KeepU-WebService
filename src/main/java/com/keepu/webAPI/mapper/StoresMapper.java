package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateStoreRequest;
import com.keepu.webAPI.dto.response.StoreResponse;
import com.keepu.webAPI.model.Stores;
import org.springframework.stereotype.Component;

@Component
public class StoresMapper {

    public StoreResponse toStoreResponse(Stores store) {
        if (store == null) {
            return null;
        }
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getLocation(),
                store.isActive(),
                store.getType(),
                store.getLink()
        );
    }

    public Stores toStoreEntity(CreateStoreRequest request) {
        if (request == null) {
            return null;
        }

        Stores store = new Stores();
        store.setName(request.name());
        store.setLocation(request.location());
        store.setActive(true);
        store.setType(request.type());
        store.setLink(request.link());
        return store;
    }
}