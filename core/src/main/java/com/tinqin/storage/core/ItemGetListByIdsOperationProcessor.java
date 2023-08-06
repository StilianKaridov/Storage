package com.tinqin.storage.core;

import com.tinqin.storage.api.operations.get.ItemGetByIdResponse;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsOperation;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsRequest;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsResponse;
import com.tinqin.storage.persistence.entity.ItemStorage;
import com.tinqin.storage.persistence.repository.ItemStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemGetListByIdsOperationProcessor implements ItemGetListByIdsOperation {

    private final ItemStorageRepository itemStorageRepository;

    @Autowired
    public ItemGetListByIdsOperationProcessor(ItemStorageRepository itemStorageRepository) {
        this.itemStorageRepository = itemStorageRepository;
    }

    @Override
    public ItemGetListByIdsResponse process(ItemGetListByIdsRequest input) {
        List<UUID> uuids = input.getIds().stream().map(UUID::fromString).toList();

        List<ItemStorage> items = this.itemStorageRepository.findByItemIdIn(uuids);

        List<ItemGetByIdResponse> mappedItems = items
                .stream()
                .map(
                        i -> ItemGetByIdResponse
                                .builder()
                                .id(String.valueOf(i.getId()))
                                .itemId(String.valueOf(i.getItemId()))
                                .price(i.getPrice())
                                .quantity(i.getQuantity())
                                .build()
                ).toList();

        return ItemGetListByIdsResponse
                .builder()
                .items(mappedItems)
                .build();
    }
}
