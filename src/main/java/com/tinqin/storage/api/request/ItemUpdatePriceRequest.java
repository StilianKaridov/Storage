package com.tinqin.storage.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ItemUpdatePriceRequest {

    @NotBlank(message = "Item id must not be blank!")
    private String itemId;

    @NotNull(message = "Price must not be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive number!")
    private BigDecimal price;
}