package com.tinqin.storage.core;

import com.tinqin.storage.core.exception.NoSuchItemException;
import com.tinqin.storage.api.operations.remove.ItemRemoveOperation;
import com.tinqin.storage.api.operations.remove.ItemRemoveRequest;
import com.tinqin.storage.api.operations.remove.ItemRemoveResponse;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemRemoveOperationProcessor implements ItemRemoveOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemRemoveOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemRemoveResponse process(ItemRemoveRequest input) {
        String itemId = input.getItemId();
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
