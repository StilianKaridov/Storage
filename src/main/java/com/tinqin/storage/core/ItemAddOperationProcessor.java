package com.tinqin.storage.core;

import com.tinqin.storage.api.exception.ExistingItemException;
import com.tinqin.storage.api.operations.add.ItemAddOperation;
import com.tinqin.storage.api.operations.add.ItemAddRequest;
import com.tinqin.storage.api.operations.add.ItemAddResponse;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemAddOperationProcessor implements ItemAddOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemAddOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemAddResponse process(ItemAddRequest input) {
        BigDecimal price = input.getPrice();

        String itemId = input.getItemId();
        UUID itemUUID = UUID.fromString(itemId);

        Optional<ItemStorage> itemOpt = this.itemStorageRepository.findFirstByItemId(itemUUID);

        if (itemOpt.isPresent()) {
            throw new ExistingItemException();
        }

        ItemStorage item = ItemStorage
                .builder()
                .itemId(itemUUID)
                .price(price)
                .quantity(0)
                .build();

        this.itemStorageRepository.save(item);

        return ItemAddResponse
                .builder()
                .itemId(itemId)
                .price(price)
                .quantity(0)
                .build();
    }
}
