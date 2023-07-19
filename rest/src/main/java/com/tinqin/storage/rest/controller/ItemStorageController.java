package com.tinqin.storage.rest.controller;

import com.tinqin.storage.api.operations.add.ItemAddOperation;
import com.tinqin.storage.api.operations.add.ItemAddRequest;
import com.tinqin.storage.api.operations.add.ItemAddResponse;
import com.tinqin.storage.api.operations.export.ItemExportOperation;
import com.tinqin.storage.api.operations.export.ItemExportRequest;
import com.tinqin.storage.api.operations.export.ItemExportResponse;
import com.tinqin.storage.api.operations.imprt.ItemImportOperation;
import com.tinqin.storage.api.operations.imprt.ItemImportRequest;
import com.tinqin.storage.api.operations.imprt.ItemImportResponse;
import com.tinqin.storage.api.operations.remove.ItemRemoveOperation;
import com.tinqin.storage.api.operations.remove.ItemRemoveRequest;
import com.tinqin.storage.api.operations.remove.ItemRemoveResponse;
import com.tinqin.storage.api.operations.update.ItemUpdateOperation;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceRequest;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemStorageController {

    private final ItemAddOperation itemAddOperation;
    private final ItemExportOperation itemExportOperation;
    private final ItemImportOperation itemImportOperation;
    private final ItemRemoveOperation itemRemoveOperation;
    private final ItemUpdateOperation itemUpdateOperation;

    @Autowired
    public ItemStorageController(
            ItemAddOperation itemAddOperation,
            ItemExportOperation itemExportOperation,
            ItemImportOperation itemImportOperation,
            ItemRemoveOperation itemRemoveOperation,
            ItemUpdateOperation itemUpdateOperation
    ) {
        this.itemAddOperation = itemAddOperation;
        this.itemExportOperation = itemExportOperation;
        this.itemImportOperation = itemImportOperation;
        this.itemRemoveOperation = itemRemoveOperation;
        this.itemUpdateOperation = itemUpdateOperation;
    }

    @PostMapping("/addItem")
    public ResponseEntity<ItemAddResponse> addItem(@Valid @RequestBody ItemAddRequest itemAddRequest) {
        ItemAddResponse response = this.itemAddOperation.process(itemAddRequest);

        return ResponseEntity.status(201).body(response);
    }

    @Transactional
    @DeleteMapping("/deleteItem")
    public ResponseEntity<ItemRemoveResponse> removeItem(@Valid @RequestBody ItemRemoveRequest itemRemoveRequest) {
        ItemRemoveResponse response = this.itemRemoveOperation.process(itemRemoveRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/importItem")
    public ResponseEntity<ItemImportResponse> importItem(@Valid @RequestBody ItemImportRequest itemImportRequest) {
        ItemImportResponse response = this.itemImportOperation.process(itemImportRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/exportItem")
    public ResponseEntity<ItemExportResponse> exportItem(@Valid @RequestBody ItemExportRequest itemExportRequest) {
        ItemExportResponse response = this.itemExportOperation.process(itemExportRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updatePrice")
    public ResponseEntity<ItemUpdatePriceResponse> updatePrice(@Valid @RequestBody ItemUpdatePriceRequest itemUpdatePriceRequest) {
        ItemUpdatePriceResponse response = this.itemUpdateOperation.process(itemUpdatePriceRequest);

        return ResponseEntity.ok(response);
    }
}
