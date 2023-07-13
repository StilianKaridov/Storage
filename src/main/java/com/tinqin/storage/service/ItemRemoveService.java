package com.tinqin.storage.service;

import com.tinqin.storage.api.request.ItemRemoveRequest;
import com.tinqin.storage.api.response.ItemRemoveResponse;

public interface ItemRemoveService {

    ItemRemoveResponse removeItem(ItemRemoveRequest itemRemoveRequest);
}
