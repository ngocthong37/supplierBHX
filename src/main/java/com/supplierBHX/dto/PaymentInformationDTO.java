package com.supplierBHX.dto;

import com.supplierBHX.Enum.PaymentInformationType;
import com.supplierBHX.entity.PurchaseOrder;
import com.supplierBHX.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentInformationDTO {
    private Integer id;
    private String informationType;
    private Integer informationId;
    private String image;
    private LocalDate createdAt;
    private Integer supplierId;
    private Integer purchaseOrderId;
    private String purchaseOrderCode;
}
