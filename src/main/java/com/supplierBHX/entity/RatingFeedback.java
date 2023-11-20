package com.supplierBHX.entity;

import com.supplierBHX.Enum.PaymentStatus;
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
    private PaymentStatus paymentStatus;
    private String feedBackFromSup;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "ratingProduct_id")
    private RatingProduct ratingProduct;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
