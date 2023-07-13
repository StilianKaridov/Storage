package com.tinqin.storage.service.impl;

import com.tinqin.storage.api.request.ItemExportRequest;
import com.tinqin.storage.api.response.ItemExportResponse;
import com.tinqin.storage.data.entity.ItemStorage;
import com.tinqin.storage.data.repository.ItemStorageRepository;
import com.tinqin.storage.exception.NegativeUpdatedQuantityException;
import com.tinqin.storage.exception.NoSuchItemException;
import com.tinqin.storage.service.ItemExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemExportServiceImpl implements ItemExportService {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemExportServiceImpl(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemExportResponse exportItem(ItemExportRequest itemExportRequest) {
        Integer quantity = itemExportRequest.getQuantity();

        String itemId = itemExportRequest.getItemId();
        UUID itemUUID = UUID.fromString(itemId);

        ItemStorage item = itemStorageRepository
                .findFirstByItemId(itemUUID)
                .orElseThrow(NoSuchItemException::new);

        int updatedQuantity = item.getQuantity() - quantity;
        if (updatedQuantity < 0) {
            throw new NegativeUpdatedQuantityException();
        }

        ItemStorage updated = ItemStorage
                .builder()
                .id(item.getId())
                .itemId(itemUUID)
                .price(item.getPrice())
                .quantity(updatedQuantity)
                .build();

        this.itemStorageRepository.save(updated);

        return ItemExportResponse
                .builder()
                .itemId(itemId)
                .quantity(updatedQuantity)
                .price(updated.getPrice())
                .build();
    }

}
