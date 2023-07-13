package com.tinqin.storage.service;

import com.tinqin.storage.api.request.ItemExportRequest;
import com.tinqin.storage.api.response.ItemExportResponse;

public interface ItemExportService {
    ItemExportResponse exportItem(ItemExportRequest itemExportRequest);
}
