package com.supplierBHX.dto;

import com.supplierBHX.Enum.PaymentResponseType;
import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.Enum.ResponseStatus;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.Product;
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
    private String paymentResponseStatus;
    private LocalDate createdAt;
    private Integer updaterId;
    private Integer creatorId;
    private Double price;
    private Double quantity;
    private Double adjustedPrice;
    private Double adjustedQuantity;
    private Integer paymentInformationId;
    private Integer productId;
    private String productName;
}
