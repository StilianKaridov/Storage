package com.tinqin.storage.service.impl;

import com.tinqin.storage.api.request.ItemUpdatePriceRequest;
import com.tinqin.storage.api.response.ItemUpdatePriceResponse;
import com.tinqin.storage.data.entity.ItemStorage;
import com.tinqin.storage.data.repository.ItemStorageRepository;
import com.tinqin.storage.exception.NoSuchItemException;
import com.tinqin.storage.service.ItemUpdatePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ItemUpdatePriceServiceImpl implements ItemUpdatePriceService {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemUpdatePriceServiceImpl(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemUpdatePriceResponse updatePrice(ItemUpdatePriceRequest itemUpdatePriceRequest) {
        BigDecimal price = itemUpdatePriceRequest.getPrice();

        String itemId = itemUpdatePriceRequest.getItemId();
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
