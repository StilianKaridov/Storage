package com.tinqin.storage.api.operations.get;

import com.tinqin.storage.api.operations.base.OperationResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemGetByIdResponse implements OperationResponse {

    private String id;

    private String itemId;

    private BigDecimal price;

    private Integer quantity;
}
