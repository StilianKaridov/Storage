package com.tinqin.storage.core;

import com.tinqin.storage.api.operations.export.ItemExportOperation;
import com.tinqin.storage.api.operations.export.ItemExportRequest;
import com.tinqin.storage.api.operations.sell.ItemsSellOperation;
import com.tinqin.storage.api.operations.sell.ItemsSellRequest;
import com.tinqin.storage.api.operations.sell.ItemsSellResponse;
import com.tinqin.storage.persistence.entity.SoldItemsHistory;
import com.tinqin.storage.persistence.repository.SoldItemsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemsSellOperationProcessor implements ItemsSellOperation {

    private final ItemExportOperation itemExportOperation;
    private final SoldItemsHistoryRepository soldItemsHistoryRepository;

    @Autowired
    public ItemsSellOperationProcessor(ItemExportOperation itemExportOperation, SoldItemsHistoryRepository soldItemsHistoryRepository) {
        this.itemExportOperation = itemExportOperation;
        this.soldItemsHistoryRepository = soldItemsHistoryRepository;
    }

    @Override
    public ItemsSellResponse process(ItemsSellRequest input) {
        input.getItems()
                .forEach(item -> {
                    ItemExportRequest build = ItemExportRequest
                            .builder()
                            .itemId(item.getItemId())
                            .quantity(item.getQuantity())
                            .build();


                    this.itemExportOperation.process(build);

                    SoldItemsHistory itemHistory = SoldItemsHistory
                            .builder()
                            .userId(UUID.fromString(item.getUserId()))
                            .itemId(UUID.fromString(item.getItemId()))
                            .price(item.getPrice())
                            .quantity(item.getQuantity())
                            .build();

                    this.soldItemsHistoryRepository.save(itemHistory);
                });

        return ItemsSellResponse
                .builder()
                .items(input.getItems())
                .build();
    }
}
