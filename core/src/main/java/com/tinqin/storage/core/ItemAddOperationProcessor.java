package com.tinqin.storage.core;

import com.tinqin.storage.api.operations.add.ItemAddOperation;
import com.tinqin.storage.api.operations.add.ItemAddRequest;
import com.tinqin.storage.api.operations.add.ItemAddResponse;
import com.tinqin.storage.core.exception.ExistingItemException;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import com.tinqin.zoostore.restexport.ZooStoreRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemAddOperationProcessor implements ItemAddOperation {

    private final ItemStorageRepository itemStorageRepository;
    private final ZooStoreRestClient zooStoreRestClient;

    @Autowired
    public ItemAddOperationProcessor(ItemStorageRepository itemStorageRepository, ZooStoreRestClient zooStoreRestClient) {
        this.itemStorageRepository = itemStorageRepository;
        this.zooStoreRestClient = zooStoreRestClient;
    }

    @Override
    public ItemAddResponse process(ItemAddRequest input) {
        try {
            zooStoreRestClient.getItemById(input.getItemId());
        } catch (Exception e) {
            throw new RuntimeException("Message!");
        }

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
