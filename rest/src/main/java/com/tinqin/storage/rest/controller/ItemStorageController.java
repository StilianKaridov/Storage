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
import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersOperation;
import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersRequest;
import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final UserCheckForOrdersOperation userCheckForOrdersOperation;

    @Autowired
    public ItemStorageController(
            ItemGetByIdOperation itemGetByIdOperation, ItemAddOperation itemAddOperation,
            ItemExportOperation itemExportOperation,
            ItemImportOperation itemImportOperation,
            ItemRemoveOperation itemRemoveOperation,
            ItemUpdateOperation itemUpdateOperation,
            ItemGetListByIdsOperation itemGetListByIdsOperation,
            ItemsSellOperation itemsSellOperation,
            UserCheckForOrdersOperation userCheckForOrdersOperation
    ) {
        this.itemGetByIdOperation = itemGetByIdOperation;
        this.itemAddOperation = itemAddOperation;
        this.itemExportOperation = itemExportOperation;
        this.itemImportOperation = itemImportOperation;
        this.itemRemoveOperation = itemRemoveOperation;
        this.itemUpdateOperation = itemUpdateOperation;
        this.itemGetListByIdsOperation = itemGetListByIdsOperation;
        this.itemsSellOperation = itemsSellOperation;
        this.userCheckForOrdersOperation = userCheckForOrdersOperation;
    }

    @Operation(description = "Gets item by id.",
            summary = "Get item by id.")
    @ApiResponse(responseCode = "200", description = "Item found.")
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "This item does not exist!"), mediaType = "text/html")})
    @GetMapping("/{id}")
    public ResponseEntity<ItemGetByIdResponse> getItemById(@PathVariable String id) {
        ItemGetByIdRequest itemRequest = ItemGetByIdRequest
                .builder()
                .itemId(id)
                .build();

        ItemGetByIdResponse response = this.itemGetByIdOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Check if current logged in user from BFF has at least one order.",
            summary = "Check if user has at least one order.")
    @ApiResponse(responseCode = "200", description = "Returns if user has orders.")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserCheckForOrdersResponse> checkIfUserHasOrders(@PathVariable String id) {
        UserCheckForOrdersRequest userRequest = UserCheckForOrdersRequest
                .builder()
                .userId(id)
                .build();

        UserCheckForOrdersResponse response = this.userCheckForOrdersOperation.process(userRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Gets all items by ids.",
            summary = "Gets items by ids.")
    @ApiResponse(responseCode = "200", description = "Items found.")
    @PostMapping("/getItems")
    public ResponseEntity<ItemGetListByIdsResponse> getCollectionOfItemsById(@RequestBody ItemGetListByIdsRequest itemIds) {
        ItemGetListByIdsResponse response = this.itemGetListByIdsOperation.process(itemIds);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Adds item to the storage.",
            summary = "Add item to the storage.")
    @ApiResponse(responseCode = "201", description = "Item added.")
    @ApiResponse(responseCode = "400",
            description = "Item is existing in the storage.",
            content = {@Content(examples = @ExampleObject(value = "This item already exists!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid item id format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item id is required.",
            content = {@Content(examples = @ExampleObject(value = "Item id must not be blank!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Price must not be null.",
            content = {@Content(examples = @ExampleObject(value = "Price must not be null!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Negative price number.",
            content = {@Content(examples = @ExampleObject(value = "Price must be a positive number!"), mediaType = "text/html")})
    @PostMapping
    public ResponseEntity<ItemAddResponse> addItem(@Valid @RequestBody ItemAddRequest itemAddRequest) {
        ItemAddResponse response = this.itemAddOperation.process(itemAddRequest);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(description = "Imports item to the storage.",
            summary = "Import item.")
    @ApiResponse(responseCode = "200", description = "Returns item id and his updated quantity and price.")
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "This item does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid item id format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item id is required.",
            content = {@Content(examples = @ExampleObject(value = "Item id must not be blank!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Quantity must not be null.",
            content = {@Content(examples = @ExampleObject(value = "Quantity must not be null!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Negative quantity number.",
            content = {@Content(examples = @ExampleObject(value = "Quantity must be a positive number!"), mediaType = "text/html")})
    @PatchMapping("/import")
    public ResponseEntity<ItemImportResponse> importItem(@Valid @RequestBody ItemImportRequest itemImportRequest) {
        ItemImportResponse response = this.itemImportOperation.process(itemImportRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Exports item from the storage.",
            summary = "Export item.")
    @ApiResponse(responseCode = "200", description = "Returns item id and his updated quantity and price.")
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "This item does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Negative updated quantity.",
            content = {@Content(examples = @ExampleObject(value = "Not enough quantity for 'itemId'!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid item id format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item id is required.",
            content = {@Content(examples = @ExampleObject(value = "Item id must not be blank!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Quantity must not be null.",
            content = {@Content(examples = @ExampleObject(value = "Quantity must not be null!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Negative quantity number.",
            content = {@Content(examples = @ExampleObject(value = "Quantity must be a positive number!"), mediaType = "text/html")})
    @PatchMapping("/export")
    public ResponseEntity<ItemExportResponse> exportItem(@Valid @RequestBody ItemExportRequest itemExportRequest) {
        ItemExportResponse response = this.itemExportOperation.process(itemExportRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Updates price on item from the storage.",
            summary = "Updates price on item.")
    @ApiResponse(responseCode = "200", description = "Returns item id and his updated quantity and price.")
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "This item does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid item id format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item id is required.",
            content = {@Content(examples = @ExampleObject(value = "Item id must not be blank!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Price must not be null.",
            content = {@Content(examples = @ExampleObject(value = "Price must not be null!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Negative price number.",
            content = {@Content(examples = @ExampleObject(value = "Price must be a positive number!"), mediaType = "text/html")})
    @PatchMapping("/update")
    public ResponseEntity<ItemUpdatePriceResponse> updatePrice(@Valid @RequestBody ItemUpdatePriceRequest itemUpdatePriceRequest) {
        ItemUpdatePriceResponse response = this.itemUpdateOperation.process(itemUpdatePriceRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Sells an item from the storage. Using item export operation.",
            summary = "Sells item.")
    @ApiResponse(responseCode = "200", description = "Successfully sold item.")
    @Transactional
    @PutMapping("/sell")
    public ResponseEntity<ItemsSellResponse> sellItems(
            @RequestBody ItemsSellRequest itemsSellRequest
    ) {
        ItemsSellResponse response = this.itemsSellOperation.process(itemsSellRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Removes item from the storage.",
            summary = "Removes item.")
    @ApiResponse(responseCode = "200", description = "Item removed.")
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "This item does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid item id format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item id is required.",
            content = {@Content(examples = @ExampleObject(value = "Item id must not be blank!"), mediaType = "text/html")})
    @Transactional
    @DeleteMapping
    public ResponseEntity<ItemRemoveResponse> removeItem(@Valid @RequestBody ItemRemoveRequest itemRemoveRequest) {
        ItemRemoveResponse response = this.itemRemoveOperation.process(itemRemoveRequest);

        return ResponseEntity.ok(response);
    }
}
