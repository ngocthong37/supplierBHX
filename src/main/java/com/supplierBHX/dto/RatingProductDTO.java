package com.supplierBHX.dto;

import com.supplierBHX.entity.Product;
import com.supplierBHX.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingProductDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private String productCode;
    private Integer priceScore;
    private Integer quantityScore;
    private Integer qualityScore;
    private Integer deliverScore;
    private LocalDate ratingDate;
    private Float ratingScore;
    private Double totalOrderQuantity;
    private Double totalDeliveredQuantity;
    private Double totalReceivedQuantity;
    private Integer createId;
    private Integer supplierId;
}
