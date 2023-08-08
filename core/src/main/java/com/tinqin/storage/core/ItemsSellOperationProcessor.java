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

import java.math.BigDecimal;
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
        UUID userId = UUID.fromString(input.getUserId());

        input.getItems()
                .forEach(item -> {
                    String itemId = item.getItemId();
                    BigDecimal price = item.getPrice();
                    BigDecimal priceWithDiscount = item.getPriceWithDiscount();
                    Integer quantity = item.getQuantity();

                    ItemExportRequest itemExport = ItemExportRequest
                            .builder()
                            .itemId(itemId)
                            .quantity(quantity)
                            .build();

                    this.itemExportOperation.process(itemExport);

                    SoldItemsHistory itemHistory = SoldItemsHistory
                            .builder()
                            .userId(userId)
                            .itemId(UUID.fromString(itemId))
                            .priceWithDiscount(priceWithDiscount)
                            .price(price)
                            .quantity(quantity)
                            .build();

                    this.soldItemsHistoryRepository.save(itemHistory);
                });

        return ItemsSellResponse
                .builder()
                .items(input.getItems())
                .build();
    }
}
