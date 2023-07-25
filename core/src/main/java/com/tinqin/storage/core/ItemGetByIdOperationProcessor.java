package com.tinqin.storage.core;

import com.tinqin.storage.api.operations.get.ItemGetByIdOperation;
import com.tinqin.storage.api.operations.get.ItemGetByIdRequest;
import com.tinqin.storage.api.operations.get.ItemGetByIdResponse;
import com.tinqin.storage.core.exception.NoSuchItemException;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemGetByIdOperationProcessor implements ItemGetByIdOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemGetByIdOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemGetByIdResponse process(ItemGetByIdRequest input) {
        UUID itemId = UUID.fromString(input.getItemId());

        ItemStorage itemStorage = this.itemStorageRepository
                .findFirstByItemId(itemId).orElseThrow(NoSuchItemException::new);

        return ItemGetByIdResponse
                .builder()
                .id(itemStorage.getId())
                .itemId(itemStorage.getItemId())
                .price(itemStorage.getPrice())
                .quantity(itemStorage.getQuantity())
                .build();
    }
}
