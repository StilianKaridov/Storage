package com.tinqin.storage.service;

import com.tinqin.storage.api.request.ItemUpdatePriceRequest;
import com.tinqin.storage.api.response.ItemUpdatePriceResponse;

public interface ItemUpdatePriceService {
    ItemUpdatePriceResponse updatePrice(ItemUpdatePriceRequest itemUpdatePriceRequest);
}
