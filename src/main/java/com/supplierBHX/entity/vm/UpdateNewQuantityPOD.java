package com.supplierBHX.entity.vm;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateNewQuantityPOD {
    @NotNull
    private Integer id;
    @NotNull
    private Double newQuantity;
}
