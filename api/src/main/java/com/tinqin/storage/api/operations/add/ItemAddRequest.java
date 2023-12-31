package com.tinqin.storage.api.operations.add;

import com.tinqin.storage.api.operations.base.OperationRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ItemAddRequest implements OperationRequest {

    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid UUID format!"
    )
    @NotBlank(message = "Item id must not be blank!")
    private String itemId;

    @NotNull(message = "Price must not be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive number!")
    private BigDecimal price;
}
