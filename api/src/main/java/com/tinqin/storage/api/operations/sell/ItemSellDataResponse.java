package com.tinqin.storage.api.operations.sell;

import com.tinqin.storage.api.operations.base.OperationResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemSellDataResponse implements OperationResponse {

    private String userId;

    private String itemId;

    private Integer quantity;

    private BigDecimal price;
}
