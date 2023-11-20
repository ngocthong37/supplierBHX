package com.supplierBHX.dto;

import com.supplierBHX.Enum.PaymentResponseType;
import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.Enum.ResponseStatus;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO {
    private Integer id;
    private String paymentResponseType;
    private String paymentStatus;
    private String paymentResponseStatus;
    private LocalDate updateDate;
    private String note;
    private Integer updater;
    private Integer purchaseOrderId;
    private Integer accountId;
}
