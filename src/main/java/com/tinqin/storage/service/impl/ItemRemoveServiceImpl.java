package com.tinqin.storage.service.impl;

import com.tinqin.storage.api.request.ItemRemoveRequest;
import com.tinqin.storage.api.response.ItemRemoveResponse;
import com.tinqin.storage.data.entity.ItemStorage;
import com.tinqin.storage.data.repository.ItemStorageRepository;
import com.tinqin.storage.exception.NoSuchItemException;
import com.tinqin.storage.service.ItemRemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemRemoveServiceImpl implements ItemRemoveService {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemRemoveServiceImpl(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemRemoveResponse removeItem(ItemRemoveRequest itemRemoveRequest) {
        String itemId = itemRemoveRequest.getItemId();
        UUID itemUUID = UUID.fromString(itemId);

        ItemStorage item = this.itemStorageRepository
                .findFirstByItemId(itemUUID)
                .orElseThrow(NoSuchItemException::new);

        this.itemStorageRepository.delete(item);

        return ItemRemoveResponse
                .builder()
                .itemId(itemId)
                .build();
    }
}
