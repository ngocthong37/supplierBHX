package com.supplierBHX.dto;

import com.supplierBHX.entity.Invoice;
import com.supplierBHX.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetailDTO {
    private Integer id;
    private Integer quantity;
    private Integer price;
    private Integer invoiceId;
    private Product product;
}
