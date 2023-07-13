package com.tinqin.storage.service.impl;

import com.tinqin.storage.api.request.ItemAddRequest;
import com.tinqin.storage.api.response.ItemAddResponse;
import com.tinqin.storage.data.entity.ItemStorage;
import com.tinqin.storage.data.repository.ItemStorageRepository;
import com.tinqin.storage.exception.ExistingItemException;
import com.tinqin.storage.service.ItemAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemAddServiceImpl implements ItemAddService {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemAddServiceImpl(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemAddResponse addItem(ItemAddRequest itemAddRequest) {
        BigDecimal price = itemAddRequest.getPrice();

        String itemId = itemAddRequest.getItemId();
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
