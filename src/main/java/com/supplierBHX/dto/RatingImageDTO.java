package com.supplierBHX.dto;

import com.supplierBHX.entity.RatingProduct;
import com.supplierBHX.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingImageDTO {
    private Integer id;
    private String image;
    private Integer ratingProductId;
}
