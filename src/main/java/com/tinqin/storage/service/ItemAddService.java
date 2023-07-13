package com.tinqin.storage.service;

import com.tinqin.storage.api.request.ItemAddRequest;
import com.tinqin.storage.api.response.ItemAddResponse;

public interface ItemAddService {
    ItemAddResponse addItem(ItemAddRequest itemAddRequest);
}
