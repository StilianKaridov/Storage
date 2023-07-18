package com.tinqin.storage.core;

import com.tinqin.storage.core.exception.NoSuchItemException;
import com.tinqin.storage.api.operations.update.ItemUpdateOperation;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceRequest;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceResponse;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ItemUpdateOperationProcessor implements ItemUpdateOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemUpdateOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemUpdatePriceResponse process(ItemUpdatePriceRequest input) {
        BigDecimal price = input.getPrice();

        String itemId = input.getItemId();
        UUID itemUUID = UUID.fromString(itemId);

        ItemStorage item = itemStorageRepository
                .findFirstByItemId(itemUUID).orElseThrow(NoSuchItemException::new);

        ItemStorage updated = ItemStorage
                .builder()
                .id(item.getId())
                .itemId(itemUUID)
                .price(price)
                .quantity(item.getQuantity())
                .build();

        this.itemStorageRepository.save(updated);

        return ItemUpdatePriceResponse
                .builder()
                .itemId(itemId)
                .price(price)
                .build();
    }
}
