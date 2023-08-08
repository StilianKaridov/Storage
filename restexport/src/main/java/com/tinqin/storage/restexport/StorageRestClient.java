package com.tinqin.storage.restexport;

import com.tinqin.storage.api.operations.add.ItemAddRequest;
import com.tinqin.storage.api.operations.add.ItemAddResponse;
import com.tinqin.storage.api.operations.export.ItemExportRequest;
import com.tinqin.storage.api.operations.export.ItemExportResponse;
import com.tinqin.storage.api.operations.get.ItemGetByIdResponse;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsRequest;
import com.tinqin.storage.api.operations.getlistofitems.ItemGetListByIdsResponse;
import com.tinqin.storage.api.operations.imprt.ItemImportRequest;
import com.tinqin.storage.api.operations.imprt.ItemImportResponse;
import com.tinqin.storage.api.operations.remove.ItemRemoveRequest;
import com.tinqin.storage.api.operations.remove.ItemRemoveResponse;
import com.tinqin.storage.api.operations.sell.ItemsSellRequest;
import com.tinqin.storage.api.operations.sell.ItemsSellResponse;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceRequest;
import com.tinqin.storage.api.operations.update.ItemUpdatePriceResponse;
import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

@Headers({"Content-Type: application/json"})
public interface StorageRestClient {

    @RequestLine("GET /api/storage/items/{id}")
    ItemGetByIdResponse getItemById(@Param String id);

    @RequestLine("GET /api/storage/items/user/{id}")
    UserCheckForOrdersResponse checkIfUserHasOrders(@Param String id);

    @RequestLine("POST /api/storage/items/getItems")
    ItemGetListByIdsResponse getCollectionOfItemsById(@RequestBody ItemGetListByIdsRequest itemIds);

    @RequestLine("POST /api/storage/items")
    ItemAddResponse addItem(@RequestBody ItemAddRequest itemAddRequest);

    @RequestLine("PATCH /api/storage/items/import")
    ItemImportResponse importItem(@RequestBody ItemImportRequest itemImportRequest);

    @RequestLine("PATCH /api/storage/items/export")
    ItemExportResponse exportItem(@RequestBody ItemExportRequest itemExportRequest);

    @RequestLine("PATCH /api/storage/items/update")
    ItemUpdatePriceResponse updatePrice(@RequestBody ItemUpdatePriceRequest itemUpdatePriceRequest);

    @RequestLine("PUT /api/storage/items/sell")
    ItemsSellResponse sellItems(@RequestBody ItemsSellRequest itemsSellRequest);

    @RequestLine("DELETE /api/storage/items")
    ItemRemoveResponse removeItem(@RequestBody ItemRemoveRequest itemRemoveRequest);

}
