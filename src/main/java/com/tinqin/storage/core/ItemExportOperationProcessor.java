package com.tinqin.storage.core;

import com.tinqin.storage.api.exception.NegativeUpdatedQuantityException;
import com.tinqin.storage.api.exception.NoSuchItemException;
import com.tinqin.storage.api.operations.export.ItemExportOperation;
import com.tinqin.storage.api.operations.export.ItemExportRequest;
import com.tinqin.storage.api.operations.export.ItemExportResponse;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemExportOperationProcessor implements ItemExportOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemExportOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemExportResponse process(ItemExportRequest input) {
        Integer quantity = input.getQuantity();

        String itemId = input.getItemId();
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
