package com.tinqin.storage.service.impl;

import com.tinqin.storage.api.request.ItemImportRequest;
import com.tinqin.storage.api.response.ItemImportResponse;
import com.tinqin.storage.data.entity.ItemStorage;
import com.tinqin.storage.data.repository.ItemStorageRepository;
import com.tinqin.storage.exception.NoSuchItemException;
import com.tinqin.storage.service.ItemImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemImportServiceImpl implements ItemImportService {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemImportServiceImpl(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemImportResponse importItem(ItemImportRequest itemImportRequest) {
        Integer quantity = itemImportRequest.getQuantity();

        String itemId = itemImportRequest.getItemId();
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
