package com.tinqin.storage.service;

import com.tinqin.storage.api.request.ItemImportRequest;
import com.tinqin.storage.api.response.ItemImportResponse;

public interface ItemImportService {
    ItemImportResponse importItem(ItemImportRequest itemImportRequest);
}
