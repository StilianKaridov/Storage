package com.tinqin.storage.core;

import com.tinqin.storage.api.exception.NoSuchItemException;
import com.tinqin.storage.api.operations.imprt.ItemImportOperation;
import com.tinqin.storage.api.operations.imprt.ItemImportRequest;
import com.tinqin.storage.api.operations.imprt.ItemImportResponse;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemImportOperationProcessor implements ItemImportOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemImportOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemImportResponse process(ItemImportRequest input) {
        Integer quantity = input.getQuantity();

        String itemId = input.getItemId();
        UUID itemUUID = UUID.fromString(itemId);

        ItemStorage item = itemStorageRepository
                .findFirstByItemId(itemUUID).orElseThrow(NoSuchItemException::new);

        int updatedQuantity = item.getQuantity() + quantity;
        ItemStorage updatedItem = ItemStorage
                .builder()
                .id(item.getId())
                .itemId(itemUUID)
                .price(item.getPrice())
                .quantity(updatedQuantity)
                .build();

        this.itemStorageRepository.save(updatedItem);

        return ItemImportResponse
                .builder()
                .itemId(itemId)
                .quantity(updatedQuantity)
                .price(updatedItem.getPrice())
                .build();
    }
}
