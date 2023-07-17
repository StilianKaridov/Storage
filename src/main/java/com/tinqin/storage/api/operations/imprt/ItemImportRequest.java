package com.tinqin.storage.api.operations.imprt;

import com.tinqin.storage.api.operations.base.OperationRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ItemImportRequest implements OperationRequest {

    @NotBlank(message = "Item id must not be blank!")
    private String itemId;

    @NotNull(message = "Quantity must not be null!")
    @Min(value = 1, message = "Quantity must be a positive number!")
    private Integer quantity;
}
