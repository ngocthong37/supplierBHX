package com.supplierBHX.dto;

import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.RatingProduct;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingFeedbackDTO {
    private Integer id;
    private String paymentStatus;
    private String feedBackFromSup;
    private LocalDate createdAt;
    private Integer ratingProductId;
    private Integer accountId;
}
