package com.supplierBHX.entity.vm;

import com.supplierBHX.Enum.UnitType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PurchaseOrderDetailVM {
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Double VAT = 0.1;
    private Double discount;
    private Double quantity;
    private Double mass;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    private Double newQuantity;
    private Double newMass;
    private Double finalAmount;
}
