package com.tinqin.storage.api.operations.getlistofitems;

import com.tinqin.storage.api.operations.base.OperationResponse;
import com.tinqin.storage.api.operations.get.ItemGetByIdResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemGetListByIdsResponse implements OperationResponse {

    private List<ItemGetByIdResponse> items;
}
