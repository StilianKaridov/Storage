package com.tinqin.storage.api.operations.sell;

import com.tinqin.storage.api.operations.base.OperationRequest;
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
public class ItemsSellRequest implements OperationRequest {

    private String userId;

    private List<ItemSellDataResponse> items;
}
