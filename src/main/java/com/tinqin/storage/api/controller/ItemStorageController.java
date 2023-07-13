package com.tinqin.storage.api.controller;

import com.tinqin.storage.api.request.ItemAddRequest;
import com.tinqin.storage.api.request.ItemExportRequest;
import com.tinqin.storage.api.request.ItemImportRequest;
import com.tinqin.storage.api.request.ItemRemoveRequest;
import com.tinqin.storage.api.request.ItemUpdatePriceRequest;
import com.tinqin.storage.api.response.ItemAddResponse;
import com.tinqin.storage.api.response.ItemExportResponse;
import com.tinqin.storage.api.response.ItemImportResponse;
import com.tinqin.storage.api.response.ItemRemoveResponse;
import com.tinqin.storage.api.response.ItemUpdatePriceResponse;
import com.tinqin.storage.service.ItemAddService;
import com.tinqin.storage.service.ItemExportService;
import com.tinqin.storage.service.ItemImportService;
import com.tinqin.storage.service.ItemRemoveService;
import com.tinqin.storage.service.ItemUpdatePriceService;
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

    private final ItemAddService itemAddService;
    private final ItemRemoveService itemRemoveService;
    private final ItemImportService itemImportService;
    private final ItemExportService itemExportService;
    private final ItemUpdatePriceService itemUpdatePriceService;

    @Autowired
    public ItemStorageController(
            ItemAddService itemAddService,
            ItemRemoveService itemRemoveService, ItemImportService itemImportService,
            ItemExportService itemExportService,
            ItemUpdatePriceService itemUpdatePriceService) {
        this.itemAddService = itemAddService;
        this.itemRemoveService = itemRemoveService;
        this.itemImportService = itemImportService;
        this.itemExportService = itemExportService;
        this.itemUpdatePriceService = itemUpdatePriceService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<ItemAddResponse> addItem(@Valid @RequestBody ItemAddRequest itemAddRequest) {
        ItemAddResponse response = this.itemAddService.addItem(itemAddRequest);

        return ResponseEntity.status(201).body(response);
    }

    @Transactional
    @DeleteMapping("/deleteItem")
    public ResponseEntity<ItemRemoveResponse> removeItem(@Valid @RequestBody ItemRemoveRequest itemRemoveRequest) {
        ItemRemoveResponse response = this.itemRemoveService.removeItem(itemRemoveRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/importItem")
    public ResponseEntity<ItemImportResponse> importItem(@Valid @RequestBody ItemImportRequest itemImportRequest) {
        ItemImportResponse response = this.itemImportService.importItem(itemImportRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/exportItem")
    public ResponseEntity<ItemExportResponse> exportItem(@Valid @RequestBody ItemExportRequest itemExportRequest) {
        ItemExportResponse response = this.itemExportService.exportItem(itemExportRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updatePrice")
    public ResponseEntity<ItemUpdatePriceResponse> updatePrice(@Valid @RequestBody ItemUpdatePriceRequest itemUpdatePriceRequest) {
        ItemUpdatePriceResponse response = this.itemUpdatePriceService.updatePrice(itemUpdatePriceRequest);

        return ResponseEntity.ok(response);
    }
}
