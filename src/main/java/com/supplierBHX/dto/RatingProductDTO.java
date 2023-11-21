package com.supplierBHX.dto;

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
public class RatingProductDTO {
    private Integer id;
    private Integer productId;
    private Float quantityScore;
    private Float qualityScore;
    private Double price;
    private LocalDate ratingDate;
    private Boolean cooperativeState;
    private Float ratingScore;
    private String note;
    private Integer supplierId;
}
