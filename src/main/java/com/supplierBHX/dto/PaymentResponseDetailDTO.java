package com.supplierBHX.dto;

import com.supplierBHX.entity.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseDetailDTO {

    private Integer id;
    private Integer productId;
    private Double priceOfInvoice;
    private Double adjustedPrice;
    private Double quantityOfInvoice;
    private Double adjustedQuantity;
    private Integer paymentResponseId;
}
