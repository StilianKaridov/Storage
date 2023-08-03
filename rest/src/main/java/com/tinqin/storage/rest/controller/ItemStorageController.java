package com.tinqin.storage.rest.controller;

import com.tinqin.storage.api.operations.add.ItemAddOperation;
import com.tinqin.storage.api.operations.add.ItemAddRequest;
import com.tinqin.storage.api.operations.add.ItemAddResponse;
import com.tinqin.storage.api.operations.export.ItemExportOperation;
import com.tinqin.storage.api.operations.export.ItemExportRequest;
import com.tinqin.storage.api.operations.export.ItemExportResponse;
import com.tinqin.storage.api.operations.get.ItemGetByIdOperation;
import com.tinqin.storage.api.operations.get.ItemGetByIdRequest;
import com.tinqin.storage.api.operations.get.ItemGetByIdResponse;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsOperation;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsRequest;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsResponse;
import com.tinqin.storage.api.operations.imprt.ItemImportOperation;
import com.tinqin.storage.api.operations.imprt.ItemImportRequest;
import com.tinqin.storage.api.operations.imprt.ItemImportResponse;
import com.tinqin.storage.api.operations.remove.ItemRemoveOperation;
import com.tinqin.storage.api.operations.remove.ItemRemoveRequest;
import com.tinqin.storage.api.operations.remove.ItemRemoveResponse;
import com.tinqin.storage.api.operations.sell.ItemsSellOperation;
import com.tinqin.storage.api.operations.sell.ItemsSellRequest;
import com.tinqin.storage.api.operations.sell.ItemsSellResponse;
import com.tinqin.storage.api.operations.update.ItemUpdateOperation;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceRequest;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storage/items")
public class ItemStorageController {

    private final ItemGetByIdOperation itemGetByIdOperation;
    private final ItemAddOperation itemAddOperation;
    private final ItemExportOperation itemExportOperation;
    private final ItemImportOperation itemImportOperation;
    private final ItemRemoveOperation itemRemoveOperation;
    private final ItemUpdateOperation itemUpdateOperation;
    private final ItemGetListByIdsOperation itemGetListByIdsOperation;
    private final ItemsSellOperation itemsSellOperation;

    @Autowired
    public ItemStorageController(
            ItemGetByIdOperation itemGetByIdOperation, ItemAddOperation itemAddOperation,
            ItemExportOperation itemExportOperation,
            ItemImportOperation itemImportOperation,
            ItemRemoveOperation itemRemoveOperation,
            ItemUpdateOperation itemUpdateOperation,
            ItemGetListByIdsOperation itemGetListByIdsOperation, ItemsSellOperation itemsSellOperation) {
        this.itemGetByIdOperation = itemGetByIdOperation;
        this.itemAddOperation = itemAddOperation;
        this.itemExportOperation = itemExportOperation;
        this.itemImportOperation = itemImportOperation;
        this.itemRemoveOperation = itemRemoveOperation;
        this.itemUpdateOperation = itemUpdateOperation;
        this.itemGetListByIdsOperation = itemGetListByIdsOperation;
        this.itemsSellOperation = itemsSellOperation;
    }

    @PostMapping("/getItems")
    public ResponseEntity<ItemGetListByIdsResponse> getCollectionOfItemsById(@RequestBody ItemGetListByIdsRequest itemIds) {
        ItemGetListByIdsResponse response = this.itemGetListByIdsOperation.process(itemIds);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemGetByIdResponse> getItemById(@PathVariable String id) {
        ItemGetByIdRequest itemRequest = ItemGetByIdRequest
                .builder()
                .itemId(id)
                .build();

        ItemGetByIdResponse response = this.itemGetByIdOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ItemAddResponse> addItem(@Valid @RequestBody ItemAddRequest itemAddRequest) {
        ItemAddResponse response = this.itemAddOperation.process(itemAddRequest);

        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/import")
    public ResponseEntity<ItemImportResponse> importItem(@Valid @RequestBody ItemImportRequest itemImportRequest) {
        ItemImportResponse response = this.itemImportOperation.process(itemImportRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/export")
    public ResponseEntity<ItemExportResponse> exportItem(@Valid @RequestBody ItemExportRequest itemExportRequest) {
        ItemExportResponse response = this.itemExportOperation.process(itemExportRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<ItemUpdatePriceResponse> updatePrice(@Valid @RequestBody ItemUpdatePriceRequest itemUpdatePriceRequest) {
        ItemUpdatePriceResponse response = this.itemUpdateOperation.process(itemUpdatePriceRequest);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PutMapping("/sell")
    public ResponseEntity<ItemsSellResponse> sellItems(
            @RequestBody ItemsSellRequest itemsSellRequest
    ) {
        ItemsSellResponse response = this.itemsSellOperation.process(itemsSellRequest);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<ItemRemoveResponse> removeItem(@Valid @RequestBody ItemRemoveRequest itemRemoveRequest) {
        ItemRemoveResponse response = this.itemRemoveOperation.process(itemRemoveRequest);

        return ResponseEntity.ok(response);
    }
}
