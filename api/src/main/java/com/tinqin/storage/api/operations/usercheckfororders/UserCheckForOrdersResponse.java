package com.tinqin.storage.api.operations.usercheckfororders;

import com.tinqin.storage.api.operations.base.OperationResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class UserCheckForOrdersResponse implements OperationResponse {

    private Boolean hasOrders;
}
