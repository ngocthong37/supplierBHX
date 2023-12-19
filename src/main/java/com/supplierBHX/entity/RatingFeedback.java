package com.supplierBHX.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.supplierBHX.Enum.PaymentStatus;
import com.supplierBHX.Enum.ResponseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RatingFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus;
    private String feedBackFromSup;
    private String feedBackFromBHX;
    private LocalDate createdAt;

    @JsonBackReference(value = "ratingProduct-ratingFeedback")
    @ManyToOne
    @JoinColumn(name = "ratingProduct_id")
    private RatingProduct ratingProduct;

    @JsonBackReference(value = "account-ratingFeedback")
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
