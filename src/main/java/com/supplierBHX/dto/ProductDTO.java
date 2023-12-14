package com.supplierBHX.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private Integer quantity;
    private Double price;
    private Integer productId;
    private String productName;

}